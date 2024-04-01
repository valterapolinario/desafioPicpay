package br.com.valter.picpaysimplificado.transferencia.infra;

import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import br.com.valter.picpaysimplificado.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferenciaJPA extends JpaRepository<Transferencia, UUID> {
}
