package br.com.valter.picpaysimplificado.transferencia.infra;
import br.com.valter.picpaysimplificado.transferencia.domain.Transferencia;
import br.com.valter.picpaysimplificado.usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class transferenciaJpaRepository implements TransferenciaRepository {
    @Autowired
    private TransferenciaJPA transferenciaJPA;

    @Override
    public Transferencia salvar(Transferencia entidade) {
        return transferenciaJPA.save(entidade);
    }
}
