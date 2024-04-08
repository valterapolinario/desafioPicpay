package br.com.valter.picpaysimplificado.transferencia.application.api;

import br.com.valter.picpaysimplificado.transferencia.application.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tranferencias")
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
