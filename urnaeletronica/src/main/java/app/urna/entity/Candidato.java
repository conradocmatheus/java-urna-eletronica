package app.urna.entity;

import app.urna.entity.Enum.StatusCandidato;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    private StatusCandidato status;

    @Transient
    private String votosApurados;

    @OneToMany(mappedBy = "prefeitoEscolhido")
    private List<Voto> votosComoPrefeito;

    @OneToMany(mappedBy = "vereadorEscolhido")
    private List<Voto> votosComoVereador;
}
