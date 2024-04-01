package br.com.valter.picpaysimplificado.usuario.infra;

import br.com.valter.picpaysimplificado.usuario.domain.Usuario;

import java.util.UUID;

public interface UsuarioRepository {

    Usuario buscaUsuarioPorId(UUID id);
}
