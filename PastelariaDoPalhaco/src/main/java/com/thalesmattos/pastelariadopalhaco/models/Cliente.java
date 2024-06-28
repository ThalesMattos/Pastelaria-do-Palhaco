package com.thalesmattos.pastelariadopalhaco.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table
@Entity
public class Cliente {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Pedido pedido;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    private Mesa mesa;

    @OneToOne(cascade = CascadeType.ALL)
    private Requisicao requisicao;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos;

}
