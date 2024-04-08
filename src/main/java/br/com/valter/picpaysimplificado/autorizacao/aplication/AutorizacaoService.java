package br.com.valter.picpaysimplificado.autorizacao.aplication;

import br.com.valter.picpaysimplificado.autorizacao.infra.Autorizacao;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class AutorizacaoService {

    String uri = "https://run.mocky.io";

    String path = "/v3";

    String code = "/5794d450-d2e2-4412-8131-73d0293ac1cc";

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
