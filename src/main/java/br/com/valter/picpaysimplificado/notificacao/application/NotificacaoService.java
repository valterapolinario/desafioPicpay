package br.com.valter.picpaysimplificado.notificacao.application;

import br.com.valter.picpaysimplificado.autorizacao.infra.Autorizacao;
import br.com.valter.picpaysimplificado.notificacao.infra.Notificacao;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class NotificacaoService {
    String uri = "https://run.mocky.io";

    String path = "/v3";

    String code = "/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";

    RestClient client = RestClient.create();

    public Notificacao notificar(){
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
