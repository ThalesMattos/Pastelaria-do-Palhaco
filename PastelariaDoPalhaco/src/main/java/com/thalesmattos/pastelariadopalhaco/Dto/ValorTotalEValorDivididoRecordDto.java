package com.thalesmattos.pastelariadopalhaco.Dto;

import jakarta.validation.constraints.NotNull;

public record ValorTotalEValorDivididoRecordDto(@NotNull float valorTotal, float valorDividido) {
}
