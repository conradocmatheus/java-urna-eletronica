package app.urna.entity;

import app.urna.entity.Enum.StatusEleitor;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private Long Id;

    @NotBlank
    private String nomeCompleto;

    @CPF
    @Nullable
    private String Cpf;

    @NotBlank
    private String profissao;

    @Pattern(regexp = "")
    private String telefoneCelular;

    @Pattern(regexp = "")
    private String telefoneFixo;

    @Email
    private String email;

    private StatusEleitor status;
}