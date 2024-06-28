package com.thalesmattos.pastelariadopalhaco.services;

import com.thalesmattos.pastelariadopalhaco.models.*;
import com.thalesmattos.pastelariadopalhaco.repository.ClienteRepository;
import com.thalesmattos.pastelariadopalhaco.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    private final RequisicaoService requisicaoService;
    private final ClienteRepository clienteRepository;

    public String savePix(int idCliente, PagamentoPix pix) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.get().getPedido().getValorDivididoPorPessoa() == pix.getValorPago()) {
            conferirDescontoPix(pix);
            pix.setDataPagamentoFoiEfetuado(LocalDate.now());
            pix.setDataParaReceberPagamento(pix.getDataPagamentoFoiEfetuado().plusDays(0));
            cliente.get().getRequisicao().setPessoasQueJaPagaram(cliente.get().getRequisicao().getPessoasQueJaPagaram() + 1);
            if (cliente.get().getPagamentos() == null) {
                cliente.get().setPagamentos(new ArrayList<>());
            }
            cliente.get().getPagamentos().add(pix);
            clienteRepository.save(cliente.get());
            return verificarSeTodosDaMesaPagaram(cliente.get());
        } else {
            return "Valor incorreto para o pagamento, valor pago deve ser: " + cliente.get().getPedido().getValorDivididoPorPessoa() + " reais";
        }
    }

    public String savePixDelivery(int idCliente, PagamentoPix pix) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.get().getPedido().getValorTotalSemTaxaDeServico() == pix.getValorPago()) {
            conferirDescontoPix(pix);
            pix.setDataPagamentoFoiEfetuado(LocalDate.now());
            pix.setDataParaReceberPagamento(pix.getDataPagamentoFoiEfetuado().plusDays(0));
            pagamentoRepository.save(pix);
            if (cliente.get().getPagamentos() == null) {
                cliente.get().setPagamentos(new ArrayList<>());
            }
            cliente.get().getPagamentos().add(pix);
            clienteRepository.save(cliente.get());
            return "Pagamento efetuado com sucesso";
        } else {
            return "Valor incorreto para o pagamento, valor pago deve ser: " + cliente.get().getPedido().getValorTotalSemTaxaDeServico() + " reais";
        }
    }

    public void conferirDescontoPix(PagamentoPix pix) {
        if (pix.getValorPago() * pix.getDESCONTO() >=10){
            pix.setValorPosDescontoDoBanco(pix.getValorPago() - 10);
            pix.setValorParaPagarAoBanco(10);
        } else{
            pix.setValorPosDescontoDoBanco(pix.getValorPago() * (1 - pix.getDESCONTO()));
            pix.setValorParaPagarAoBanco(pix.getValorPago() * pix.getDESCONTO());
        }
        pagamentoRepository.save(pix);
    }

    public String saveCredito(int idCliente, PagamentoCredito credito) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.get().getPedido().getValorDivididoPorPessoa() == credito.getValorPago()) {
            credito.setValorPosDescontoDoBanco(credito.getValorPago() * (1 - credito.getDESCONTO()));
            credito.setValorParaPagarAoBanco(credito.getValorPago() * credito.getDESCONTO());
            credito.setDataPagamentoFoiEfetuado(LocalDate.now());
            credito.setDataParaReceberPagamento(credito.getDataPagamentoFoiEfetuado().plusDays(30));
            cliente.get().getRequisicao().setPessoasQueJaPagaram(cliente.get().getRequisicao().getPessoasQueJaPagaram() + 1);
            if (cliente.get().getPagamentos() == null) {
                cliente.get().setPagamentos(new ArrayList<>());
            }
            cliente.get().getPagamentos().add(credito);
            clienteRepository.save(cliente.get());
            return verificarSeTodosDaMesaPagaram(cliente.get());
        } else {
            return "Valor incorreto para o pagamento, valor pago deve ser: " + cliente.get().getPedido().getValorDivididoPorPessoa() + " reais";
        }
    }

    public String saveCreditoDelivery(int idCliente, PagamentoCredito credito) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.get().getPedido().getValorTotalSemTaxaDeServico() == credito.getValorPago()) {
            credito.setValorPosDescontoDoBanco(credito.getValorPago() * (1 - credito.getDESCONTO()));
            credito.setValorParaPagarAoBanco(credito.getValorPago() * credito.getDESCONTO());
            credito.setDataPagamentoFoiEfetuado(LocalDate.now());
            credito.setDataParaReceberPagamento(LocalDate.now().plusDays(30));
            if (cliente.get().getPagamentos() == null) {
                cliente.get().setPagamentos(new ArrayList<>());
            }
            cliente.get().getPagamentos().add(credito);
            clienteRepository.save(cliente.get());
            return "Pagamento efetuado com sucesso";
        } else {
            return "Valor incorreto para o pagamento, valor pago deve ser: " + cliente.get().getPedido().getValorTotalSemTaxaDeServico() + " reais";
        }
    }

    public String saveDebito(int idCliente, PagamentoDebito debito) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.get().getPedido().getValorDivididoPorPessoa() == debito.getValorPago()) {
            debito.setValorPosDescontoDoBanco(debito.getValorPago() * (1 - debito.getDESCONTO()));
            debito.setValorParaPagarAoBanco(debito.getValorPago() * debito.getDESCONTO());
            debito.setDataPagamentoFoiEfetuado(LocalDate.now());
            debito.setDataParaReceberPagamento(debito.getDataPagamentoFoiEfetuado().plusDays(14));
            cliente.get().getRequisicao().setPessoasQueJaPagaram(cliente.get().getRequisicao().getPessoasQueJaPagaram() + 1);
            if (cliente.get().getPagamentos() == null) {
                cliente.get().setPagamentos(new ArrayList<>());
            }
            cliente.get().getPagamentos().add(debito);
            clienteRepository.save(cliente.get());
            return verificarSeTodosDaMesaPagaram(cliente.get());
        } else {
            return "Valor incorreto para o pagamento, valor pago deve ser: " + cliente.get().getPedido().getValorDivididoPorPessoa() + " reais";
        }
    }

    public String saveDebitoDelivery(int idCliente, PagamentoDebito debito) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.get().getPedido().getValorTotalSemTaxaDeServico() == debito.getValorPago()) {
            debito.setValorPosDescontoDoBanco(debito.getValorPago() * (1 - debito.getDESCONTO()));
            debito.setValorParaPagarAoBanco(debito.getValorPago() * debito.getDESCONTO());
            debito.setDataPagamentoFoiEfetuado(LocalDate.now());
            debito.setDataParaReceberPagamento(LocalDate.now().plusDays(14));
            if (cliente.get().getPagamentos() == null) {
                cliente.get().setPagamentos(new ArrayList<>());
            }
            cliente.get().getPagamentos().add(debito);
            clienteRepository.save(cliente.get());
            return "Pagamento efetuado com sucesso";
        } else {
            return "Valor incorreto para o pagamento, valor pago deve ser: " + cliente.get().getPedido().getValorTotalSemTaxaDeServico() + " reais";
        }
    }

    public String saveDinheiro(int idCliente, PagamentoDinheiro dinheiro) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.get().getPedido().getValorDivididoPorPessoa() == dinheiro.getValorPago()) {
            dinheiro.setValorPosDescontoDoBanco(dinheiro.getValorPago() * (1 - dinheiro.getDESCONTO()));
            dinheiro.setValorParaPagarAoBanco(dinheiro.getValorPago() * dinheiro.getDESCONTO());
            dinheiro.setDataPagamentoFoiEfetuado(LocalDate.now());
            dinheiro.setDataParaReceberPagamento(dinheiro.getDataPagamentoFoiEfetuado().plusDays(0));
            cliente.get().getRequisicao().setPessoasQueJaPagaram(cliente.get().getRequisicao().getPessoasQueJaPagaram() + 1);
            if (cliente.get().getPagamentos() == null) {
                cliente.get().setPagamentos(new ArrayList<>());
            }
            cliente.get().getPagamentos().add(dinheiro);
            clienteRepository.save(cliente.get());
            return verificarSeTodosDaMesaPagaram(cliente.get());
        } else {
            return "Valor incorreto para o pagamento, valor pago deve ser: " + cliente.get().getPedido().getValorDivididoPorPessoa() + " reais";
        }
    }

    public String saveDinheiroDelivery(int idCliente, PagamentoDinheiro dinheiro) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if(cliente.get().getPedido().getValorTotalSemTaxaDeServico() == dinheiro.getValorPago()) {
            dinheiro.setValorPosDescontoDoBanco(dinheiro.getValorPago() * (1 - dinheiro.getDESCONTO()));
            dinheiro.setValorParaPagarAoBanco(dinheiro.getValorPago() * dinheiro.getDESCONTO());
            dinheiro.setDataPagamentoFoiEfetuado(LocalDate.now());
            dinheiro.setDataParaReceberPagamento(LocalDate.now().plusDays(0));
            if (cliente.get().getPagamentos() == null) {
                cliente.get().setPagamentos(new ArrayList<>());
            }
            cliente.get().getPagamentos().add(dinheiro);
            clienteRepository.save(cliente.get());
            return "Pagamento efetuado com sucesso";
        } else {
            return "Valor incorreto para o pagamento, valor pago deve ser: " + cliente.get().getPedido().getValorTotalSemTaxaDeServico() + " reais";
        }
    }

    public List<Pagamento> getPagamentosByDia(LocalDate dia) {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        return pagamentos.stream()
                .filter(pagamento -> pagamento.getDataPagamentoFoiEfetuado().equals(dia))
                .collect(Collectors.toList());
    }

    public List<Pagamento> getVendasAReceber(LocalDate dia) {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        return pagamentos.stream()
                .filter(pagamento -> pagamento.getDataParaReceberPagamento().equals(dia))
                .collect(Collectors.toList());
    }

    public String verificarSeTodosDaMesaPagaram(Cliente cliente) {
        if (cliente.getRequisicao().getPessoasQueJaPagaram() == cliente.getRequisicao().getNumPessoas()) {
            cliente.getMesa().setOcupada(false);
            cliente.setMesa(null);
            cliente.getRequisicao().setHoraSaida(LocalTime.now());
            clienteRepository.save(cliente);
            requisicaoService.verificarMesaDisponivelParaAlocar();
            return "Todos da mesa pagaram";
        } else if(cliente.getRequisicao().getPessoasQueJaPagaram() < cliente.getRequisicao().getNumPessoas()) {
            return "Ainda falta " +
                    (cliente.getRequisicao().getNumPessoas() - cliente.getRequisicao().getPessoasQueJaPagaram())
                    + " pagamentos de " +
                    cliente.getPedido().getValorDivididoPorPessoa();
        } else {
            return "Não é possível enviar mais pagamentos";
        }
    }
}
