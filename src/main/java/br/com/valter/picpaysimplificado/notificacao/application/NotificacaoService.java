package br.com.valter.picpaysimplificado.notificacao.application;

import br.com.valter.picpaysimplificado.notificacao.infra.Notificacao;
import br.com.valter.picpaysimplificado.notificacao.infra.NotificacaoProducer;
import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class NotificacaoService {
    @Value("${app.notify.url}")
    String uri;

    @Value("${app.notify.path}")
    String path;

    @Value("${app.notify.code}")
    String code;

    RestClient client = RestClient.create();

    @Autowired
    NotificacaoProducer producer;

    public Notificacao notificar(Transferencia transferencia){
        producer.enviarMensagem(transferencia);
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
