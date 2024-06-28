package com.thalesmattos.pastelariadopalhaco.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table
public class Requisicao {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = true)
    private int numPessoas;

    @Column(nullable = true)
    private int pessoasQueJaPagaram = 0;

    @Column(nullable = true)
    private float distanciaEmKm;

    @Column(nullable = true)
    private float taxaDeEntrega;

    @Column
    private final float TAXAPORKM = 2.5f;

    @Column
    private String nome;

    @Column(nullable = true)
    private LocalTime horaEntrada;

    @Column(nullable = true)
    private LocalTime horaSaida;

    @Column(nullable = true)
    private LocalDate data;

    @Column(nullable = true)
    private boolean requisicaoDelivery;

}
