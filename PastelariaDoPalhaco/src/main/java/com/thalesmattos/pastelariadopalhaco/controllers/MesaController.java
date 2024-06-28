package com.thalesmattos.pastelariadopalhaco.controllers;

import com.thalesmattos.pastelariadopalhaco.models.Mesa;
import com.thalesmattos.pastelariadopalhaco.repository.MesaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaRepository mesaRepository;

    @PostMapping
    public ResponseEntity<Mesa> saveMesa(@RequestBody Mesa mesa){
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaRepository.save(mesa));
    }

    @GetMapping
    public ResponseEntity<List<Mesa>> getMesas(){
        return ResponseEntity.status(HttpStatus.OK).body(mesaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMesaById(@PathVariable(value = "id") int id){
        Optional<Mesa> mesa = mesaRepository.findById(id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mesa.get());
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa não encontrada.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateMesa(@PathVariable(value = "id") int id,
                                             @RequestBody @Valid Mesa mesa){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(mesaRepository.save(mesa));
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa não encontrada");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMesa(@PathVariable(value = "id") int id){
        Optional<Mesa> mesa = mesaRepository.findById(id);
        try{
            mesaRepository.delete(mesa.get());
            return ResponseEntity.status(HttpStatus.OK).body("Mesa deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mesa não encontrada");
        }
    }
}
