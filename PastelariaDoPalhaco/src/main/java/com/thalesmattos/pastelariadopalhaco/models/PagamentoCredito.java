package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class PagamentoCredito extends Pagamento{

    @Column
    private String bandeiraCartao;

    private final float DESCONTO = 0.031f;

    private final int PRAZO = 30;
}
