package com.thalesmattos.pastelariadopalhaco;

import com.thalesmattos.pastelariadopalhaco.Dto.ListaDeProtudosDto;
import com.thalesmattos.pastelariadopalhaco.Dto.ValorTotalEValorDivididoRecordDto;
import com.thalesmattos.pastelariadopalhaco.models.Cliente;
import com.thalesmattos.pastelariadopalhaco.models.Pedido;
import com.thalesmattos.pastelariadopalhaco.models.Produto;
import com.thalesmattos.pastelariadopalhaco.models.Requisicao;
import com.thalesmattos.pastelariadopalhaco.repository.ClienteRepository;
import com.thalesmattos.pastelariadopalhaco.repository.PedidoRepository;
import com.thalesmattos.pastelariadopalhaco.repository.ProdutoRepository;
import com.thalesmattos.pastelariadopalhaco.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePedido() {
        int idCliente = 1;
        List<Integer> idsProdutos = Arrays.asList(1, 2, 3);
        ListaDeProtudosDto listaDeProtudosDto = new ListaDeProtudosDto();
        listaDeProtudosDto.setIdProdutos(idsProdutos);

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        Pedido pedido = new Pedido();
        List<Produto> produtos = Arrays.asList(new Produto(), new Produto(), new Produto());

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findAllById(idsProdutos)).thenReturn(produtos);

        pedidoService.savePedido(idCliente, listaDeProtudosDto);

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        assertEquals(produtos, cliente.getPedido().getProdutos());
    }

    @Test
    void testSavePedidoDelivery() {
        int idCliente = 1;
        List<Integer> idsProdutos = Arrays.asList(1, 2, 3);
        ListaDeProtudosDto listaDeProtudosDto = new ListaDeProtudosDto();
        listaDeProtudosDto.setIdProdutos(idsProdutos);

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        Pedido pedido = new Pedido();
        List<Produto> produtos = Arrays.asList(new Produto(), new Produto(), new Produto());

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findAllById(idsProdutos)).thenReturn(produtos);

        pedidoService.savePedidoDelivery(idCliente, listaDeProtudosDto);

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        assertEquals(produtos, cliente.getPedido().getProdutos());
        assertEquals(true, cliente.getPedido().isDelivery());
    }

    @Test
    void testUpdatePedido() {
        int idCliente = 1;
        List<Integer> idsProdutos = Arrays.asList(1, 2, 3);
        ListaDeProtudosDto listaDeProtudosDto = new ListaDeProtudosDto();
        listaDeProtudosDto.setIdProdutos(idsProdutos);

        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        Pedido pedido = new Pedido();
        cliente.setPedido(pedido);
        List<Produto> produtos = Arrays.asList(new Produto(), new Produto(), new Produto());

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        when(produtoRepository.findAllById(idsProdutos)).thenReturn(produtos);

        pedidoService.updatePedido(idCliente, listaDeProtudosDto);

        verify(clienteRepository, times(1)).save(any(Cliente.class));
        assertEquals(produtos, cliente.getPedido().getProdutos());
    }

    @Test
    void testEncerrarPedido() {
        int idCliente = 1;
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        Requisicao requisicao = new Requisicao();
        requisicao.setDistanciaEmKm(10);
        requisicao.setNumPessoas(4);
        cliente.setRequisicao(requisicao);

        Pedido pedido = new Pedido();
        Produto produto1 = new Produto();
        produto1.setValor(10);
        Produto produto2 = new Produto();
        produto2.setValor(20);
        Produto produto3 = new Produto();
        produto3.setValor(30);

        pedido.setProdutos(Arrays.asList(produto1, produto2, produto3));
        cliente.setPedido(pedido);

        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        Object result = pedidoService.encerrarPedido(idCliente);

        float valorTotal = 10 + 20 + 30;
        float valorComTaxa = valorTotal * 1.1f;
        float valorDividido = valorComTaxa / 4;

        assertEquals(new ValorTotalEValorDivididoRecordDto(valorComTaxa, valorDividido), result);

        verify(clienteRepository, times(1)).save(any(Cliente.class));
        assertEquals(valorTotal, pedido.getValorTotalSemTaxaDeServico());
        assertEquals(valorComTaxa, pedido.getValorTotalComTaxaDeServico());
        assertEquals(valorDividido, pedido.getValorDivididoPorPessoa());
    }
}
