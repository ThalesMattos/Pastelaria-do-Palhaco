package com.thalesmattos.pastelariadopalhaco.controllers;

import com.thalesmattos.pastelariadopalhaco.models.Cliente;
import com.thalesmattos.pastelariadopalhaco.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    @GetMapping("/{idCliente}")
    public ResponseEntity<Object> getClienteById(@PathVariable(value = "idCliente") int idCliente){
        try{
            Optional<Cliente> cliente = clienteRepository.findById(idCliente);
            return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getTodosClientes(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.findAll());
    }
}
