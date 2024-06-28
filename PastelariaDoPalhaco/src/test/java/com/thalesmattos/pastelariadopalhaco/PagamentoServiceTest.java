package com.thalesmattos.pastelariadopalhaco;

import com.thalesmattos.pastelariadopalhaco.models.*;
import com.thalesmattos.pastelariadopalhaco.repository.ClienteRepository;
import com.thalesmattos.pastelariadopalhaco.repository.PagamentoRepository;
import com.thalesmattos.pastelariadopalhaco.services.PagamentoService;
import com.thalesmattos.pastelariadopalhaco.services.RequisicaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService pagamentoService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private RequisicaoService requisicaoService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;
    private Pedido pedido;
    private Requisicao requisicao;
    private Mesa mesa;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mesa = new Mesa();
        mesa.setCapacidade(4);
        mesa.setOcupada(true);

        requisicao = new Requisicao();
        requisicao.setNumPessoas(4);
        requisicao.setPessoasQueJaPagaram(0);

        pedido = new Pedido();
        pedido.setValorDivididoPorPessoa(50);
        pedido.setValorTotalSemTaxaDeServico(200);

        cliente = new Cliente();
        cliente.setMesa(mesa);
        cliente.setRequisicao(requisicao);
        cliente.setPedido(pedido);
    }

    @Test
    public void testSavePix() {
        PagamentoPix pix = new PagamentoPix();
        pix.setValorPago(50);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        String response = pagamentoService.savePix(1, pix);

        assertEquals("Ainda falta 3 pagamentos de 50.0", response);
        verify(clienteRepository, times(1)).save(cliente);
        verify(pagamentoRepository, times(1)).save(pix);
    }

    @Test
    public void testSaveCredito() {
        PagamentoCredito credito = new PagamentoCredito();
        credito.setValorPago(50);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        String response = pagamentoService.saveCredito(1, credito);

        assertEquals("Ainda falta 3 pagamentos de 50.0", response);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testSaveDebito() {
        PagamentoDebito debito = new PagamentoDebito();
        debito.setValorPago(50);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        String response = pagamentoService.saveDebito(1, debito);

        assertEquals("Ainda falta 3 pagamentos de 50.0", response);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testSaveDinheiro() {
        PagamentoDinheiro dinheiro = new PagamentoDinheiro();
        dinheiro.setValorPago(50);

        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        String response = pagamentoService.saveDinheiro(1, dinheiro);

        assertEquals("Ainda falta 3 pagamentos de 50.0", response);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testGetPagamentosByDia() {
        Pagamento pagamento = new Pagamento();
        pagamento.setDataPagamentoFoiEfetuado(LocalDate.now());
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pagamento);

        when(pagamentoRepository.findAll()).thenReturn(pagamentos);

        List<Pagamento> result = pagamentoService.getPagamentosByDia(LocalDate.now());

        assertEquals(1, result.size());
        assertEquals(pagamento, result.get(0));
    }

    @Test
    public void testGetVendasAReceber() {
        Pagamento pagamento = new Pagamento();
        pagamento.setDataParaReceberPagamento(LocalDate.now());
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pagamento);

        when(pagamentoRepository.findAll()).thenReturn(pagamentos);

        List<Pagamento> result = pagamentoService.getVendasAReceber(LocalDate.now());

        assertEquals(1, result.size());
        assertEquals(pagamento, result.get(0));
    }

    @Test
    public void testVerificarSeTodosDaMesaPagaram() {
        requisicao.setPessoasQueJaPagaram(4);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        String response = pagamentoService.verificarSeTodosDaMesaPagaram(cliente);

        assertEquals("Todos da mesa pagaram", response);
        assertFalse(cliente.getMesa().isOcupada());
        assertNull(cliente.getMesa());
    }
}
