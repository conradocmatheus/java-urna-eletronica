package app.urna.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Voto {

    private LocalDateTime dataHora;

    private String prefeitoEscolhido;

    private String vereadorEscolhido;

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
