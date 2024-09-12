package app.urna.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.security.PrivateKey;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Candidato {

    @NotBlank(message = "O nome não pode ser nulo.")
    private String nomeCompleto;

    @NotNull
    @CPF(message = "CPF Inválido. O formato deve ser 123.456.789.09")
    private String cpf;

    @NotNull
    @Column(unique = true)
    private String numeroCandidato;

    @NotBlank(message = "A Funcao não pode ser nulo.")
    private String funcao;

    private String status;

    @Transient
    private String votosApurados;


}
