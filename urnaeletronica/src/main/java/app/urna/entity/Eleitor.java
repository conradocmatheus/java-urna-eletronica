package app.urna.entity;

import app.urna.entity.Enum.StatusEleitor;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Eleitor extends Pessoa {

    @NotBlank(message = "O nome não pode ser nulo nem vazio.")
    private String profissao;

    @NotNull(message = "O telefoneCelular não deve ser nulo.")
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}")
    private String telefoneCelular;

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4}-\\d{4}")
    private String telefoneFixo;

    @Email
    private String email;

    private StatusEleitor status;
}