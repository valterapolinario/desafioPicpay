package br.com.valter.picpaysimplificado.notificacao.application;

import br.com.valter.picpaysimplificado.notificacao.infra.NotificacaoProducer;
import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    @Autowired
    NotificacaoProducer producer;

    public void notificar(Transferencia transferencia){
        producer.enviarMensagem(transferencia);
    }

}
