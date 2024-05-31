package br.com.valter.picpaysimplificado.transferencia.application.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;


public interface TransferenciaApi {

    @PostMapping
    @ResponseStatus(CREATED)
    ResponseEntity<TransferenciaResponse> executaTrasnferencia(@RequestBody @Valid TransferenciaRequest request);
}
