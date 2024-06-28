package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table
@Entity
public class ListaDeEspera {

    @Id
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cliente> clientes;
}
