package com.thalesmattos.pastelariadopalhaco;

import com.thalesmattos.pastelariadopalhaco.models.Cliente;
import com.thalesmattos.pastelariadopalhaco.models.ListaDeEspera;
import com.thalesmattos.pastelariadopalhaco.models.Mesa;
import com.thalesmattos.pastelariadopalhaco.models.Requisicao;
import com.thalesmattos.pastelariadopalhaco.repository.ClienteRepository;
import com.thalesmattos.pastelariadopalhaco.repository.ListaDeEsperaRepository;
import com.thalesmattos.pastelariadopalhaco.repository.MesaRepository;
import com.thalesmattos.pastelariadopalhaco.repository.RequisicaoRepository;
import com.thalesmattos.pastelariadopalhaco.services.RequisicaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class RequisicaoServiceTest {

    @InjectMocks
    private RequisicaoService requisicaoService;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private ListaDeEsperaRepository listaDeEsperaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RequisicaoRepository requisicaoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAlocarMesaWithoutPessoas() {
        Requisicao requisicao = new Requisicao();
        requisicao.setNumPessoas(0);
        requisicao.setRequisicaoDelivery(true);
        requisicao.setHoraEntrada(LocalTime.now());
        requisicao.setData(LocalDate.now());

        Cliente cliente = new Cliente();

        when(requisicaoRepository.save(any(Requisicao.class))).thenReturn(requisicao);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        requisicaoService.alocarMesa(requisicao);

        verify(requisicaoRepository, times(1)).save(any(Requisicao.class));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testAlocarMesaWithPessoasAndMesaDisponivel() {
        Requisicao requisicao = new Requisicao();
        requisicao.setNumPessoas(4);

        List<Mesa> mesasDisponiveis = new ArrayList<>();
        Mesa mesaDisponivel = new Mesa();
        mesaDisponivel.setId(1);
        mesaDisponivel.setCapacidade(4);
        mesasDisponiveis.add(mesaDisponivel);

        Cliente cliente = new Cliente();

        when(mesaRepository.findAll()).thenReturn(mesasDisponiveis);
        when(requisicaoRepository.save(any(Requisicao.class))).thenReturn(requisicao);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        requisicaoService.alocarMesa(requisicao);

        verify(mesaRepository, times(1)).findAll();
        verify(requisicaoRepository, times(1)).save(any(Requisicao.class));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testAlocarMesaWithPessoasAndSemMesaDisponivel() {
        Requisicao requisicao = new Requisicao();
        requisicao.setNumPessoas(6);

        List<Mesa> mesasDisponiveis = new ArrayList<>();

        ListaDeEspera listaDeEspera = new ListaDeEspera();
        listaDeEspera.setId(1);
        List<Cliente> clientesEspera = new ArrayList<>();
        listaDeEspera.setClientes(clientesEspera);

        when(mesaRepository.findAll()).thenReturn(mesasDisponiveis);
        when(listaDeEsperaRepository.findById(1)).thenReturn(Optional.of(listaDeEspera));
        when(requisicaoRepository.save(any(Requisicao.class))).thenReturn(requisicao);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente());

        requisicaoService.alocarMesa(requisicao);

        verify(mesaRepository, times(1)).findAll();
        verify(listaDeEsperaRepository, times(1)).findById(1);
        verify(requisicaoRepository, times(1)).save(any(Requisicao.class));
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testVerificarMesaDisponivelParaAlocar() {
        ListaDeEspera listaDeEspera = new ListaDeEspera();
        Cliente cliente1 = new Cliente();
        Requisicao requisicao1 = new Requisicao();
        requisicao1.setNumPessoas(4);
        cliente1.setRequisicao(requisicao1);
        listaDeEspera.setClientes(List.of(cliente1));

        Mesa mesaDisponivel = new Mesa();
        mesaDisponivel.setId(1);
        mesaDisponivel.setCapacidade(4);

        List<Mesa> mesasDisponiveis = new ArrayList<>();
        mesasDisponiveis.add(mesaDisponivel);

        when(listaDeEsperaRepository.findById(1)).thenReturn(Optional.of(listaDeEspera));
        when(mesaRepository.findAll()).thenReturn(mesasDisponiveis);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente1);

        requisicaoService.verificarMesaDisponivelParaAlocar();

        verify(listaDeEsperaRepository, times(1)).findById(1);
        verify(mesaRepository, times(1)).findAll();
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}
