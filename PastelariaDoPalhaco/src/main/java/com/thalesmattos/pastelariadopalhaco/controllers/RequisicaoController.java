package com.thalesmattos.pastelariadopalhaco.controllers;

import com.thalesmattos.pastelariadopalhaco.repository.ListaDeEsperaRepository;
import com.thalesmattos.pastelariadopalhaco.services.RequisicaoService;
import com.thalesmattos.pastelariadopalhaco.models.Requisicao;
import com.thalesmattos.pastelariadopalhaco.repository.RequisicaoRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requisicoes")
public class RequisicaoController{

    private final RequisicaoRepository requisicaoRepository;
    private final RequisicaoService requisicaoService;
    private final ListaDeEsperaRepository listaDeEsperaRepository;

    @PostMapping
    public ResponseEntity<Object> saveRequisicao(@RequestBody Requisicao requisicao){
        requisicaoService.alocarMesa(requisicao);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Requisição criada com sucesso");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Requisicao>> getRequisicao(){
        return ResponseEntity.status(HttpStatus.OK).body(requisicaoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRequisicaoById(@PathVariable(value = "id") int id){
        Optional<Requisicao> requisicao = requisicaoRepository.findById(id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(requisicao.get());
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requisição não encontrada.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateRequisicao(@RequestBody @Valid Requisicao requisicao){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(requisicaoRepository.save(requisicao));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requisição não encontrada");
        }
    }

    @GetMapping("/lista-de-espera")
    public ResponseEntity<Object> getListaDeEspera(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(listaDeEsperaRepository.findById(1).get());
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lista de espera vazia");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRequisicao(@PathVariable(value = "id") int id){
        Optional<Requisicao> requisicao = requisicaoRepository.findById(id);
        try{
            requisicaoRepository.delete(requisicao.get());
            return ResponseEntity.status(HttpStatus.OK).body("Requisição deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requisição não encontrada");
        }
    }
}
