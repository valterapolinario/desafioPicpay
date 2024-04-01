package br.com.valter.picpaysimplificado.usuario.infra;
import br.com.valter.picpaysimplificado.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioJPA extends JpaRepository<Usuario, UUID> {
}
