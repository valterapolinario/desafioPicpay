package br.com.valter.picpaysimplificado.transferencia.application.service;

import br.com.valter.picpaysimplificado.transferencia.application.api.TransferenciaRequest;
import br.com.valter.picpaysimplificado.transferencia.application.api.TransferenciaResponse;

public interface TransferenciaService {

    TransferenciaResponse realizaTransferencia(TransferenciaRequest request);
}
