package br.com.valter.picpaysimplificado.transferencia.application.api;

import br.com.valter.picpaysimplificado.transferencia.application.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class TransferenciaController implements TransferenciaApi{

    @Autowired
    private TransferenciaService service;
    @Override
    public ResponseEntity<TransferenciaResponse> executaTrasnferencia(TransferenciaRequest request) {
        return ResponseEntity
                .ok()
                .body(service.realizaTransferencia(request));
    }
}
