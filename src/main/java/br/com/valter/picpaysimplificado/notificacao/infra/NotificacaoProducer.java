package br.com.valter.picpaysimplificado.notificacao.infra;

import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoProducer {

    @Value("${app.kafka.notificacao.topic}")
    private String topic;

    @Autowired
    KafkaTemplate<String,Transferencia> template;

    public void enviarMensagem( Transferencia transferencia){
        template.send(topic,transferencia);
    }
}
