package com.thalesmattos.pastelariadopalhaco.controllers;

import com.thalesmattos.pastelariadopalhaco.models.Produto;
import com.thalesmattos.pastelariadopalhaco.repository.ProdutoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<Produto> saveProduto(@RequestBody Produto produto){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getProduto(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProdutoById(@PathVariable(value = "id") int id){
        Optional<Produto> produto = produtoRepository.findById(id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(produto.get());
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrada.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduto(@RequestBody @Valid Produto produto){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable(value = "id") int id){
        Optional<Produto> produto = produtoRepository.findById(id);
        try{
            produtoRepository.delete(produto.get());
            return ResponseEntity.status(HttpStatus.OK).body("Produto deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrada");
        }
    }
}
