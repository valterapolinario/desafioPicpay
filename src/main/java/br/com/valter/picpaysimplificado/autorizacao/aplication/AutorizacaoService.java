package br.com.valter.picpaysimplificado.autorizacao.aplication;

import br.com.valter.picpaysimplificado.autorizacao.infra.Autorizacao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class AutorizacaoService {

    @Value("${app.auth.url}")
    String uri;

    @Value("${app.auth.path}")
    String path;

    @Value("${app.auth.code}")
    String code;

    RestClient client = RestClient.create();

    public Autorizacao autorizar(){
        return client
                .get()
                .uri(uri + path + code)
                .retrieve()
                .onStatus(HttpStatusCode::isError,((request, response) -> {
                    throw new RestClientResponseException("serviço indisponivel",response.getStatusCode(), response.getStatusText(),response.getHeaders(),null,null);
                }))
                .body(Autorizacao.class);
    }

}
