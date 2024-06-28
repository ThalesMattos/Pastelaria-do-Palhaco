package com.thalesmattos.pastelariadopalhaco.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class PagamentoDinheiro extends Pagamento{

    private final float DESCONTO = 0;

    private final int PRAZO = 0;
}
