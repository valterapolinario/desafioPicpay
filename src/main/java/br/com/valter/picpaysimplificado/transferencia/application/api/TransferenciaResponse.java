package br.com.valter.picpaysimplificado.transferencia.application.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferenciaResponse(

        UUID id,
        BigDecimal valor,

        UUID pagador,

        UUID recebedor,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "UTF-8")
        LocalDateTime dataTransferencia) {
}
