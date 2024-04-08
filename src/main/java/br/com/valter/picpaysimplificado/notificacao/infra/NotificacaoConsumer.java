package br.com.valter.picpaysimplificado.notificacao.infra;

import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class NotificacaoConsumer {
    @Value("${app.notify.url}")
    String uri;

    @Value("${app.notify.path}")
    String path;

    @Value("${app.notify.code}")
    String code;

    RestClient client = RestClient.create();



    @KafkaListener(groupId = "${app.kafka.notificacao.groupId}",topics = "${app.kafka.notificacao.topic}")
    public void processarNotificacao(@Payload Transferencia transferencia, Acknowledgment acknowledgment){
        notificar();
        acknowledgment.acknowledge();
    }

    private Notificacao notificar(){
        return client
                .get()
                .uri(uri + path + code)
                .retrieve()
                .onStatus(HttpStatusCode::isError,((request, response) -> {
                    throw new RestClientResponseException("serviço indisponivel",response.getStatusCode(), response.getStatusText(),response.getHeaders(),null,null);
                }))
                .body(Notificacao.class);
    }
}
