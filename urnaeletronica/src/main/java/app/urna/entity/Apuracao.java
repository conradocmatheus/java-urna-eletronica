package app.urna.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apuracao {

    private int totalVotos;

    private Map<Candidato, Integer> votosPrefeitos;

    private Map<Candidato, Integer> votosVereadores;
}
