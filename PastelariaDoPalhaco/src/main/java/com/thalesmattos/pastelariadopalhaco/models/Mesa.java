package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    @Column
    private int capacidade;
    @Column
    private boolean ocupada;
}
