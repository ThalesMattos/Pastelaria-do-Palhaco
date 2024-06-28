package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class PagamentoDebito extends Pagamento{

    @Column
    private String nomeBanco;

    private final float DESCONTO = 0.014f;

    private final int PRAZO = 14;
}
