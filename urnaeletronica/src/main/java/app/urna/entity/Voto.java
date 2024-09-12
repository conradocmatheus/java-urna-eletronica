package app.urna.entity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Voto {

    private Date    dataHora ;

    private String  prefeitoEscolhido;

    private String  vereadorEscolhido;

    private  String hash;




}
