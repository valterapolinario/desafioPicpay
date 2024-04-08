package br.com.valter.picpaysimplificado.transferencia.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Getter
@Table(name = "TRANSFERENCIAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false, unique = true)
    private UUID id;

    private UUID pagador;

    private UUID recebedor;


    private BigDecimal valor;


    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).truncatedTo(ChronoUnit.SECONDS);
    }

}