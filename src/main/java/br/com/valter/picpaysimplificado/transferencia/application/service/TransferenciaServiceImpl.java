package br.com.valter.picpaysimplificado.transferencia.application.service;

import br.com.valter.picpaysimplificado.autorizacao.aplication.AutorizacaoService;
import br.com.valter.picpaysimplificado.exception.ErroValidacaoException;
import br.com.valter.picpaysimplificado.notificacao.application.NotificacaoService;
import br.com.valter.picpaysimplificado.transferencia.application.api.TransferenciaRequest;
import br.com.valter.picpaysimplificado.transferencia.application.api.TransferenciaResponse;
import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import br.com.valter.picpaysimplificado.transferencia.infra.TransferenciaRepository;
import br.com.valter.picpaysimplificado.usuario.infra.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TransferenciaRepository repository;

    @Autowired
    AutorizacaoService autorizacaoService;

    @Autowired
    NotificacaoService notificacaoService;

    @Override
    @Transactional
    public TransferenciaResponse realizaTransferencia(TransferenciaRequest request) {

        var autorizacao = autorizacaoService.autorizar();

        if (!autorizacao.isAutorizado()){
            throw new ErroValidacaoException("usuario não esta autorizado");
        }
        if (request.pagador().equals(request.recebedor())){
            throw new ErroValidacaoException("recebedor e pagador iguais");
        }
        var pagador  = usuarioRepository.buscaUsuarioPorId(request.pagador()).orElseThrow(()-> new ErroValidacaoException("pagador nao localizado"));
        var recebedor = usuarioRepository.buscaUsuarioPorId(request.recebedor()).orElseThrow(()-> new ErroValidacaoException("recebedor nao localizado"));

        if (pagador.getTipo().isLojista()){
            throw new ErroValidacaoException("lojista nao pode efetuar pagamento");
        }
        if (pagador.getCarteira().compareTo(request.valor()) <= 0){
            throw new ErroValidacaoException("saldo insuficiente");
        }

        Transferencia transferencia = Transferencia
                .builder()
                .valor(request.valor())
                .pagador(request.pagador())
                .recebedor(request.recebedor())
                .build();

        var novaTransferencia = repository
                .salvar(transferencia);

        pagador.debitar(request.valor());
        recebedor.creditar(request.valor());

        usuarioRepository.salvarUsuario(pagador);
        usuarioRepository.salvarUsuario(recebedor);
        // 4 - notificar
        notificacaoService.notificar();
        return new TransferenciaResponse(novaTransferencia.getId(),
                novaTransferencia.getValor(),
                novaTransferencia.getPagador(),
                novaTransferencia.getRecebedor(),
                novaTransferencia.getDataCriacao());
    }

    /*
    * - pagador for do tipo comum
    * - pagador tem saldo positivo
    * - pagador e recebedor nao podem ser iguais
    *
    * */
}
