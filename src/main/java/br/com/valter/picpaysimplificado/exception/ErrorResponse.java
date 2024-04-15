package br.com.valter.picpaysimplificado.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        String error,
        Integer status,

        LocalDateTime time

) {
}
