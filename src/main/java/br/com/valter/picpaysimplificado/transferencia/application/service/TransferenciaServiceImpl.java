package br.com.valter.picpaysimplificado.transferencia.application.service;

import br.com.valter.picpaysimplificado.autorizacao.aplication.AutorizacaoService;
import br.com.valter.picpaysimplificado.autorizacao.infra.Autorizacao;
import br.com.valter.picpaysimplificado.exception.ErroValidacaoException;
import br.com.valter.picpaysimplificado.notificacao.application.NotificacaoService;
import br.com.valter.picpaysimplificado.transferencia.application.api.TransferenciaRequest;
import br.com.valter.picpaysimplificado.transferencia.application.api.TransferenciaResponse;
import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import br.com.valter.picpaysimplificado.transferencia.infra.TransferenciaRepository;
import br.com.valter.picpaysimplificado.usuario.domain.Usuario;
import br.com.valter.picpaysimplificado.usuario.infra.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    public static final String PAGADOR = "pagador";
    public static final String RECEBEDOR = "recebedor";
    public static final String NAO_AUTORIZADO = "Usuário não está autorizado";
    public static final String RECEBEDOR_PAGADOR_INVALIDOS = "Recebedor e pagador não podem ser iguais";
    public static final String NAO_ENCONTRADO = " não localizado";
    public static final String TIPO_IVALIDO_PARA_TRANSACAO = "Lojista não pode efetuar pagamento";
    public static final String SALDO_INSUFICIENTE = "Saldo insuficiente";
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

        verificarAutorizacao();
        Usuario pagador = buscarUsuarioPorId(request.pagador(), PAGADOR);
        Usuario recebedor = buscarUsuarioPorId(request.recebedor(), RECEBEDOR);

        validarTransferencia(request, pagador);

        Transferencia transferencia = criarTransferencia(request);
        Transferencia novaTransferencia = repository.salvar(transferencia);

        atualizarSaldos(pagador, recebedor, request.valor());

        notificarTransferencia(novaTransferencia);

        return criarTransferenciaResponse(novaTransferencia);
    }
    /*
     * - pagador for do tipo comum
     * - pagador tem saldo positivo
     * - pagador e recebedor nao podem ser iguais
     *
     * */
    private void validarTransferencia(TransferenciaRequest request, Usuario pagador) {
        validarUsuario(request);
        validarTipo(pagador);
        validarSaldo(pagador, request.valor());
    }

    private void verificarAutorizacao() {
        Autorizacao autorizacao = autorizacaoService.autorizar();
        if (!autorizacao.isAutorizado()) {
            throw new ErroValidacaoException(NAO_AUTORIZADO);
        }
    }

    private void validarUsuario(TransferenciaRequest request) {
        if (request.pagador().equals(request.recebedor())) {
            throw new ErroValidacaoException(RECEBEDOR_PAGADOR_INVALIDOS);
        }
    }

    private void processamentoEmBlocos(Integer tamanhoBloco){
        List<Usuario> registros = usuarioRepository.findAll();
        registros
                .stream()
                .collect(Collectors.groupingBy(Usuario::getId))
                .forEach((chave, bloco) -> processarBloco(bloco));
    }

    private void processarBloco(List<Usuario> bloco) {
    }

    private Usuario buscarUsuarioPorId(UUID id, String tipoUsuario) {
        return usuarioRepository.buscaUsuarioPorId(id)
                .orElseThrow(() -> new ErroValidacaoException(tipoUsuario + NAO_ENCONTRADO));
    }

    private void validarTipo(Usuario pagador) {
        if (pagador.getTipo().isLojista()) {
            throw new ErroValidacaoException(TIPO_IVALIDO_PARA_TRANSACAO);
        }
    }

    private void validarSaldo(Usuario pagador, BigDecimal valorTransferencia) {
        if (pagador.getCarteira().compareTo(valorTransferencia) < 0) {
            throw new ErroValidacaoException(SALDO_INSUFICIENTE);
        }
    }

    private Transferencia criarTransferencia(TransferenciaRequest request) {
        return Transferencia.builder()
                .valor(request.valor())
                .pagador(request.pagador())
                .recebedor(request.recebedor())
                .build();
    }

    private void atualizarSaldos(Usuario pagador, Usuario recebedor, BigDecimal valorTransferencia) {
        pagador.debitar(valorTransferencia);
        recebedor.creditar(valorTransferencia);
        usuarioRepository.salvarUsuario(pagador);
        usuarioRepository.salvarUsuario(recebedor);
    }

    @Async
    private void notificarTransferencia(Transferencia transferencia) {
        notificacaoService.notificar(transferencia);
    }

    private TransferenciaResponse criarTransferenciaResponse(Transferencia transferencia) {
        return new TransferenciaResponse(
                transferencia.getId(),
                transferencia.getValor(),
                transferencia.getPagador(),
                transferencia.getRecebedor(),
                transferencia.getDataCriacao()
        );
    }

}
