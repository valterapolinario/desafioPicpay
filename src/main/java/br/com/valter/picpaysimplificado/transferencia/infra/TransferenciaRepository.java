package br.com.valter.picpaysimplificado.transferencia.infra;

import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import br.com.valter.picpaysimplificado.usuario.domain.Usuario;

import java.util.UUID;

public interface TransferenciaRepository {

    Transferencia salvar(Transferencia entidade);
}
