package app.urna.entity;

import app.urna.entity.Enum.StatusEleitor;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Eleitor {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "O nome não pode ser nulo.")
    private String nomeCompleto;

    @CPF(message = "CPF Inválido. O formato deve ser 123.456.789.09")
    @Nullable
    private String Cpf;

    @NotBlank(message = "O nome não pode ser nulo.")
    private String profissao;

    @NotNull (message = "O telefoneCelular não deve ser nulo.")
    @Pattern(regexp ="\\(\\d{2}\\) \\d{4,5}-\\d{4}")
    private String telefoneCelular;

    @Pattern(regexp ="\\(\\d{2}\\) \\d{4}-\\d{4}")
    private String telefoneFixo;

    @Email
    private String email;

    private StatusEleitor status;
}