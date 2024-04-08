package br.com.valter.picpaysimplificado.transferencia.application.service;

import br.com.valter.picpaysimplificado.autorizacao.aplication.AutorizacaoService;
import br.com.valter.picpaysimplificado.exception.ErroValidacaoException;
import br.com.valter.picpaysimplificado.transferencia.application.api.TransferenciaRequest;
import br.com.valter.picpaysimplificado.transferencia.application.api.TransferenciaResponse;
import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import br.com.valter.picpaysimplificado.transferencia.infra.TransferenciaRepository;
import br.com.valter.picpaysimplificado.usuario.domain.TipoUsuarioEnum;
import br.com.valter.picpaysimplificado.usuario.domain.Usuario;
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

    @Override
    @Transactional
    public TransferenciaResponse realizaTransferencia(TransferenciaRequest request) {

        var autorizacao = autorizacaoService.autorizar();

        if (!autorizacao.autorizado()){
            throw new ErroValidacaoException("usuario não esta autorizado");
        }
        if (request.pagador().equals(request.recebedor())){
            throw new ErroValidacaoException("recebedor e pagador iguais");
        }
        // 1 - validar


        var pagador  = usuarioRepository.buscaUsuarioPorId(request.pagador()).orElseThrow(()-> new ErroValidacaoException("pagador nao localizado"));
        var recebedor = usuarioRepository.buscaUsuarioPorId(request.recebedor()).orElseThrow(()-> new ErroValidacaoException("recebedor nao localizado"));

        if (pagador.getTipo().isLojista()){
            throw new ErroValidacaoException("logista nao pode efetuar pagamento");
        }
        if (pagador.getCarteira().compareTo(request.valor()) >= 0){
            throw new ErroValidacaoException("saldo insuficiente");
        }

        Transferencia transferencia = Transferencia
                .builder()
                .valor(request.valor())
                .pagador(request.pagador())
                .recebedor(request.recebedor())
                .build();
        validarTransferencia(transferencia);
        // 2 - criar a transferencia


        var novaTransferencia = repository
                .salvar(transferencia);

        pagador.debitar(request.valor());
        recebedor.creditar(request.valor());

        usuarioRepository.salvarUsuario(pagador);
        usuarioRepository.salvarUsuario(recebedor);

        // 3 - debitar da carteira

        var usuarioPagador = usuarioRepository
                .buscaUsuarioPorId(request.pagador());
//        usuarioRepository.salvarUsuario(usuarioPagador.realizarTransferencia(request.valor()));


        // 4 - notificar
        return null;
    }

    /*
    * - pagador for do tipo comum
    * - pagador tem saldo positivo
    * - pagador e recebedor nao podem ser iguais
    *
    * */
    private void validarTransferencia(Transferencia transferencia) {
        usuarioRepository
                .buscaUsuarioPorId(transferencia.getRecebedor())
                .map(recebedor -> usuarioRepository.buscaUsuarioPorId(transferencia.getPagador())
                        .map(pagador -> isTransferenciaValida(transferencia,pagador) ? transferencia : null)
                        .orElseThrow(()-> new ErroValidacaoException("Transferencia invalida - %s".formatted(transferencia))))
                .orElseThrow(()-> new ErroValidacaoException("Transferencia invalida - %s".formatted(transferencia)));
    }

    private static boolean isTransferenciaValida(Transferencia transferencia,Usuario pagador) {
        return TipoUsuarioEnum.COMUM.equals(pagador.getTipo())
                && pagador.getCarteira().compareTo(transferencia.getValor()) >= 0
                && !pagador.getId().equals(transferencia.getRecebedor());
    }
}
