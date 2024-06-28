package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table
public class PagamentoPix extends Pagamento{

    @Column
    private String nomeEmitente;

    private final float DESCONTO = 0.0145f;

    private final int PRAZO = 0;
}
