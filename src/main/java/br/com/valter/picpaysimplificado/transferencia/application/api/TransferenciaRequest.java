package br.com.valter.picpaysimplificado.transferencia.application.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferenciaRequest(
        @NotNull
        @PositiveOrZero
        BigDecimal valor,
        @Null
        UUID pagador,
        @Null
        UUID recebedor
) {
}
