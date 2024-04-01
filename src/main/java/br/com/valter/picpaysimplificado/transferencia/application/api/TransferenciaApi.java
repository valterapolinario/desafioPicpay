package br.com.valter.picpaysimplificado.transferencia.application.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tranferencias")
public interface TransferenciaApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<TransferenciaResponse> executaTrasnferencia(@RequestBody @Valid TransferenciaRequest request);
}
