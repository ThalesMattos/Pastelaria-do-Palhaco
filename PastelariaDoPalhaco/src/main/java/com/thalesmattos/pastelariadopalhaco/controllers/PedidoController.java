package com.thalesmattos.pastelariadopalhaco.controllers;

import com.thalesmattos.pastelariadopalhaco.Dto.ListaDeProtudosDto;
import com.thalesmattos.pastelariadopalhaco.models.Pedido;
import com.thalesmattos.pastelariadopalhaco.repository.ClienteRepository;
import com.thalesmattos.pastelariadopalhaco.repository.PedidoRepository;
import com.thalesmattos.pastelariadopalhaco.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;

    @PostMapping("/{idCliente}")
    public ResponseEntity<Object> savePedido(@PathVariable(value = "idCliente") int idCliente,
                                             @RequestBody ListaDeProtudosDto listaDeProtudosDto) {
        try{
            pedidoService.savePedido(idCliente, listaDeProtudosDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido salvo com sucesso");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
    }
    }

    @PostMapping("/delivery/{idCliente}")
    public ResponseEntity<Object> savePedidoDelivery(@PathVariable(value = "idCliente") int idCliente,
                                                     @RequestBody ListaDeProtudosDto listaDeProtudosDto) {
        pedidoService.savePedidoDelivery(idCliente, listaDeProtudosDto);
        return ResponseEntity.ok("Pedido cadastrado com sucesso");
    }

    @PostMapping("/update/{idCliente}")
    public ResponseEntity<Object> updatePedido(@PathVariable(value = "idCliente") int idCliente,
                                               @RequestBody ListaDeProtudosDto listaDeProtudosDto) {
        try {
            pedidoService.updatePedido(idCliente, listaDeProtudosDto);
            return ResponseEntity.ok("Pedido atualizado");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPedidoById(@PathVariable(value = "id") int id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pedido.get());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrada.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getTodosPedidosDelivery() {
        List<Pedido> pedido = pedidoRepository.findAll();
        List<Pedido> pedidosDelivery = pedido.stream()
                .filter(Pedido::isDelivery)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDelivery);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Object> encerrarPedido(@PathVariable(value = "idCliente") int idCliente) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.encerrarPedido(idCliente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrada");
        }
    }
}
