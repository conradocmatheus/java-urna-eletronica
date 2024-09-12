package app.urna.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apuracao {

    private int totalVotos;

    private List<Candidato> prefeitos;

    private List<Candidato> vereadores;
}
