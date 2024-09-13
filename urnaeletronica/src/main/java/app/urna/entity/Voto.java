package app.urna.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "prefeito_id", nullable = false) // Define a chave estrangeira para o candidato prefeito
    private Candidato prefeitoEscolhido;

    @ManyToOne
    @JoinColumn(name = "vereador_id", nullable = false) // Define a chave estrangeira para o candidato vereador
    private Candidato vereadorEscolhido;

    @Column(nullable = false, unique = true)
    private String hash;

    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
        this.hash = gerarHash();
    }

    private String gerarHash() {
        return UUID.randomUUID().toString();
    }
}
