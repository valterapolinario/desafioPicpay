package br.com.valter.picpaysimplificado.usuario.infra;
import br.com.valter.picpaysimplificado.usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioJpaRepository implements UsuarioRepository {
    @Autowired
    private UsuarioJPA usuarioJPA;

    @Override
    public Optional<Usuario> buscaUsuarioPorId(UUID id) {
        return usuarioJPA.findById(id);
    }

    @Override
    public Usuario salvarUsuario(Usuario entidade) {
        return usuarioJPA.save(entidade);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioJPA.findAll();
    }

}
