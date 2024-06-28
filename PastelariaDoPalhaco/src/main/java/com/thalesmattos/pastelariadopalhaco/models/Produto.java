package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Produto {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String tipo;

    @Column
    private float valor;
}
