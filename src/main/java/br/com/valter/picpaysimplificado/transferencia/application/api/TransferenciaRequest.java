package br.com.valter.picpaysimplificado.transferencia.application.api;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferenciaRequest(
        @NotNull
        @PositiveOrZero
        BigDecimal valor,
        @NotNull
        UUID pagador,
        @NotNull
        UUID recebedor
) {
}
