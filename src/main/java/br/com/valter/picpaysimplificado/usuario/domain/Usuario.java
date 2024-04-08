package br.com.valter.picpaysimplificado.usuario.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false, unique = true)
    private UUID id;

    private String nome;

    private String documento;

    @Column(unique = true)
    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    private TipoUsuarioEnum tipo;

    private BigDecimal carteira;

    @CreationTimestamp
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
    @Version
    private Long version;

    public void debitar(BigDecimal valor){
       carteira = carteira.subtract(valor);
    }

    public void creditar( BigDecimal valor){
        carteira = carteira.add(valor);
    }
}
