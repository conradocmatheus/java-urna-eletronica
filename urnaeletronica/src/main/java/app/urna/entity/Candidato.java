package app.urna.entity;

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

    private String status;

    @Transient
    private String votosApurados;

    @OneToMany(mappedBy = "prefeitoEscolhido")
    private List<Voto> votosComoPrefeito; // Lista de votos onde o candidato é o prefeito

    @OneToMany(mappedBy = "vereadorEscolhido")
    private List<Voto> votosComoVereador; // Lista de votos onde o candidato é o vereador
}
