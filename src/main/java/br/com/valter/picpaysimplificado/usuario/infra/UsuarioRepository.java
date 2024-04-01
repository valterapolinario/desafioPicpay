package br.com.valter.picpaysimplificado.usuario.infra;

import br.com.valter.picpaysimplificado.usuario.domain.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    Optional<Usuario> buscaUsuarioPorId(UUID id);

    Usuario salvarUsuario( Usuario entidade);
}
