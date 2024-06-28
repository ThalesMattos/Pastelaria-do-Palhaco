package com.thalesmattos.pastelariadopalhaco.services;

import com.thalesmattos.pastelariadopalhaco.models.Cliente;
import com.thalesmattos.pastelariadopalhaco.models.ListaDeEspera;
import com.thalesmattos.pastelariadopalhaco.repository.ClienteRepository;
import com.thalesmattos.pastelariadopalhaco.repository.ListaDeEsperaRepository;
import com.thalesmattos.pastelariadopalhaco.models.Mesa;
import com.thalesmattos.pastelariadopalhaco.models.Requisicao;
import com.thalesmattos.pastelariadopalhaco.repository.MesaRepository;
import com.thalesmattos.pastelariadopalhaco.repository.RequisicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequisicaoService {

    private final MesaRepository mesaRepository;
    private final ListaDeEsperaRepository listaDeEsperaRepository;
    private final ClienteRepository clienteRepository;
    private final RequisicaoRepository requisicaoRepository;

    public void alocarMesa(Requisicao requisicao) {
        Cliente cliente = new Cliente();
        if (requisicao.getNumPessoas() == 0) {
            requisicao.setHoraEntrada(LocalTime.now());
            requisicao.setData(LocalDate.now());
            requisicao.setRequisicaoDelivery(true);
            requisicaoRepository.save(requisicao);
            cliente.setRequisicao(requisicao);
            clienteRepository.save(cliente);
        } else {
            List<Mesa> allMesas = mesaRepository.findAll();
            Optional<Mesa> mesaCompativel = allMesas.stream()
                    .filter(m -> !m.isOcupada() && m.getCapacidade() >= requisicao.getNumPessoas())
                    .findFirst();
            if (mesaCompativel.isPresent()) {
                Mesa mesa = mesaCompativel.get();
                mesa.setOcupada(true);
                requisicao.setHoraEntrada(LocalTime.now());
                requisicao.setData(LocalDate.now());
                requisicaoRepository.save(requisicao);
                cliente.setRequisicao(requisicao);
                cliente.setMesa(mesa);
                clienteRepository.save(cliente);
            } else {
                ListaDeEspera listaDeEspera = listaDeEsperaRepository.findById(1).orElseGet(() -> {
                    ListaDeEspera novaListaDeEspera = new ListaDeEspera();
                    novaListaDeEspera.setId(1);
                    return novaListaDeEspera;
                });

                if (listaDeEspera.getClientes() == null) {
                    listaDeEspera.setClientes(new ArrayList<>());
                }
                cliente.setRequisicao(requisicao);
                clienteRepository.save(cliente);
                requisicaoRepository.save(requisicao);
                listaDeEspera.getClientes().add(cliente);
                listaDeEsperaRepository.save(listaDeEspera);
            }
        }
    }

    public void verificarMesaDisponivelParaAlocar() {
        Optional<ListaDeEspera> listaDeEspera = listaDeEsperaRepository.findById(1);
        List<Mesa> allMesas = mesaRepository.findAll();
        if (listaDeEspera.isPresent()) {
            List<Cliente> clientes = listaDeEspera.get().getClientes();
            Iterator<Cliente> iterator = clientes.iterator();

            while (iterator.hasNext()) {
                Cliente cliente = iterator.next();
                Requisicao requisicao = cliente.getRequisicao();

                if (requisicao != null && requisicao.getNumPessoas() != 0) {
                    Optional<Mesa> mesaDisponivel = allMesas.stream()
                            .filter(mesa -> !mesa.isOcupada() && mesa.getCapacidade() >= requisicao.getNumPessoas())
                            .findFirst();

                    if (mesaDisponivel.isPresent()) {
                        Mesa mesa = mesaDisponivel.get();
                        cliente.setMesa(mesa);
                        mesa.setOcupada(true);
                        iterator.remove();
                        listaDeEsperaRepository.save(listaDeEspera.get());
                        clienteRepository.save(cliente);
                        mesaRepository.save(mesa);
                    }
                }
            }
        }
    }
}
