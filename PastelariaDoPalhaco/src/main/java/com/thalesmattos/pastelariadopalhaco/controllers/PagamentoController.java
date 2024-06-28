package com.thalesmattos.pastelariadopalhaco.controllers;

import com.thalesmattos.pastelariadopalhaco.models.*;
import com.thalesmattos.pastelariadopalhaco.services.PagamentoService;
import com.thalesmattos.pastelariadopalhaco.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoRepository pagamentoRepository;

    @PostMapping("/pix/{idCliente}")
    public ResponseEntity<Object> savePix(@PathVariable(value = "idCliente") int idCliente,
                                  @RequestBody PagamentoPix pix) {
        return ResponseEntity.ok(pagamentoService.savePix(idCliente, pix));
    }

    @PostMapping("/pix/delivery/{idCliente}")
    public ResponseEntity<Object> savePixDelivery(@PathVariable(value = "idCliente") int idCliente,
                                                  @RequestBody PagamentoPix pix) {
        return ResponseEntity.ok(pagamentoService.savePixDelivery(idCliente, pix));
    }

    @PostMapping("/credito/{idCliente}")
    public ResponseEntity<Object> saveCredito(@PathVariable(value = "idCliente") int idCliente,
                                              @RequestBody PagamentoCredito credito) {
        return ResponseEntity.ok(pagamentoService.saveCredito(idCliente, credito));

    }

    @PostMapping("/credito/delivery/{idCliente}")
    public ResponseEntity<Object> saveCreditoDelivery(@PathVariable(value = "idCliente") int idCliente,
                                                      @RequestBody PagamentoCredito credito) {
        return ResponseEntity.ok(pagamentoService.saveCreditoDelivery(idCliente, credito));

    }

    @PostMapping("/debito/{idCliente}")
    public ResponseEntity<Object> saveDebito(@PathVariable(value = "idCliente") int idCliente,
                                             @RequestBody PagamentoDebito debito) {
        return ResponseEntity.ok(pagamentoService.saveDebito(idCliente, debito));

    }

    @PostMapping("/debito/delivery/{idCliente}")
    public ResponseEntity<Object> saveDebitoDelivery(@PathVariable(value = "idCliente") int idCliente,
                                                     @RequestBody PagamentoDebito debito) {
        return ResponseEntity.ok(pagamentoService.saveDebitoDelivery(idCliente, debito));

    }

    @PostMapping("/dinheiro/{idCliente}")
    public ResponseEntity<Object> saveDinheiro(@PathVariable(value = "idCliente") int idCliente,
                                               @RequestBody PagamentoDinheiro dinheiro) {
        return ResponseEntity.ok(pagamentoService.saveDinheiro(idCliente, dinheiro));
    }

    @PostMapping("/dinheiro/delivery/{idCliente}")
    public ResponseEntity<Object> saveDinheiroDelivery(@PathVariable(value = "idCliente") int idCliente,
                                                       @RequestBody PagamentoDinheiro dinheiro) {
        return ResponseEntity.ok(pagamentoService.saveDinheiroDelivery(idCliente, dinheiro));
    }

    @GetMapping("/{dia}")
    public ResponseEntity<Optional<List<Pagamento>>> getVendasDoDia(@PathVariable(value = "dia") LocalDate dia) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(pagamentoService.getPagamentosByDia(dia)));
    }

    @GetMapping("/aReceber/{dia}")
    public ResponseEntity<Optional<List<Pagamento>>> getVendasAReceber(@PathVariable(value = "dia") LocalDate dia) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(pagamentoService.getVendasAReceber(dia)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePagamento(@PathVariable(value = "id") int id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);
        try {
            pagamentoRepository.delete(pagamento.get());
            return ResponseEntity.status(HttpStatus.OK).body("Pagamento deletada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrada");
        }
    }
}
