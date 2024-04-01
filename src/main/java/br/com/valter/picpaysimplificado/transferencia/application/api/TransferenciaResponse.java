package br.com.valter.picpaysimplificado.transferencia.application.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferenciaResponse(

        BigDecimal valor,

        UUID pagador,

        UUID recebedor,

        LocalDateTime dataTransferencia) {
}
