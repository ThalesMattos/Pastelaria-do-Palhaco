package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = true)
    private float valorTotalSemTaxaDeServico;

    @Column(nullable = true)
    private float valorTotalComTaxaDeServico;

    @Column(nullable = true)
    private float valorDivididoPorPessoa;

    @Column(nullable = true)
    private boolean delivery;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Produto> produtos;
}
