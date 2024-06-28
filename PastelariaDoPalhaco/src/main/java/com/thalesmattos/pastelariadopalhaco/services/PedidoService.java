package com.thalesmattos.pastelariadopalhaco.services;

import com.thalesmattos.pastelariadopalhaco.Dto.ListaDeProtudosDto;
import com.thalesmattos.pastelariadopalhaco.Dto.ValorTotalEValorDivididoRecordDto;
import com.thalesmattos.pastelariadopalhaco.models.Cliente;
import com.thalesmattos.pastelariadopalhaco.models.Pedido;
import com.thalesmattos.pastelariadopalhaco.models.Produto;
import com.thalesmattos.pastelariadopalhaco.repository.ClienteRepository;
import com.thalesmattos.pastelariadopalhaco.repository.PedidoRepository;
import com.thalesmattos.pastelariadopalhaco.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService{

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public void savePedido(int idCliente, ListaDeProtudosDto listaDeProtudosDto){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        Pedido pedido = new Pedido();
        pedido.setProdutos(produtoRepository.findAllById(listaDeProtudosDto.getIdProdutos()));
        pedidoRepository.save(pedido);
        cliente.get().setPedido(pedido);
        clienteRepository.save(cliente.get());
    }

    public void savePedidoDelivery(int idCliente, ListaDeProtudosDto listaDeProtudosDto){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        Pedido pedido = new Pedido();
        pedido.setDelivery(true);
        pedido.setProdutos(produtoRepository.findAllById(listaDeProtudosDto.getIdProdutos()));
        pedidoRepository.save(pedido);
        cliente.get().setPedido(pedido);
        clienteRepository.save(cliente.get());
    }

    public void updatePedido(int idCliente, ListaDeProtudosDto listaDeProtudosDto) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        List<Produto> listaDeIdProdutos = produtoRepository.findAllById(listaDeProtudosDto.getIdProdutos());
        cliente.get().getPedido().getProdutos().addAll(listaDeIdProdutos);
        clienteRepository.save(cliente.get());
    }

    public Object encerrarPedido(int idCliente){
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        var listaDeProdutosPedidos = cliente.get().getPedido().getProdutos();
        float valorTotal = 0;
        float valorDivididoPorPessoa = 0;
        for (var produto : listaDeProdutosPedidos){
            valorTotal += produto.getValor();
        }
        if(cliente.get().getMesa() == null){
            float distanciaEmKm = cliente.get().getRequisicao().getDistanciaEmKm();
            float taxaPorKm = cliente.get().getRequisicao().getTAXAPORKM();
            cliente.get().getPedido().setValorTotalSemTaxaDeServico(valorTotal + (distanciaEmKm * taxaPorKm));
            clienteRepository.save(cliente.get());
            return "O valor do pedido " + valorTotal +
                    " reais + taxa de entrega de " + (distanciaEmKm * taxaPorKm) +
                    " reais da um total de: " + (valorTotal + (distanciaEmKm * taxaPorKm)) + " reais";
        } else {
            valorDivididoPorPessoa = (valorTotal * 1.1f) / cliente.get().getRequisicao().getNumPessoas();
            cliente.get().getPedido().setValorTotalSemTaxaDeServico(valorTotal);
            cliente.get().getPedido().setValorTotalComTaxaDeServico(valorTotal * 1.1f);
            cliente.get().getPedido().setValorDivididoPorPessoa(valorDivididoPorPessoa);
            clienteRepository.save(cliente.get());
            return new ValorTotalEValorDivididoRecordDto((valorTotal * 1.1f), valorDivididoPorPessoa);
        }
    }
}
