package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Pagamento {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private float valorPago;

    @Column(nullable = true)
    private float valorPosDescontoDoBanco;

    @Column(nullable = true)
    private float valorParaPagarAoBanco;

    @Column(nullable = true)
    private LocalDate dataPagamentoFoiEfetuado;

    @Column(nullable = true)
    private LocalDate dataParaReceberPagamento;
}
