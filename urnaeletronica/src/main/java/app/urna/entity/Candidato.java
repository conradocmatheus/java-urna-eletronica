package app.urna.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Candidato extends Pessoa {

    @NotNull
    @Column(unique = true)
    private String numeroCandidato;

    @NotNull(message = "A Funcao não pode ser nula.")
    private int funcao;

    private String status;

    @Transient
    private String votosApurados;
}
