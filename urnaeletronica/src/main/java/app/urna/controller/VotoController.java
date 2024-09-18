package app.urna.controller;

import app.urna.entity.Apuracao;
import app.urna.entity.Voto;
import app.urna.handler.exception.NotFoundException;
import app.urna.handler.exception.WrongCandidateException;
import app.urna.service.VotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;


    @PostMapping("/votar/{idEleitor}")
    public ResponseEntity<String> votar(
            @PathVariable Long idEleitor,
            @RequestBody @Valid Voto voto) {

        try {

            String hash = votoService.votar(voto, idEleitor);
            return ResponseEntity.status(HttpStatus.CREATED).body(hash);  // Retorna 201 Created com o hash
        } catch (NotFoundException | InvalidParameterException | WrongCandidateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  // Retorna 400 Bad Request em caso de erro
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");  // Retorna 500 Internal Server Error em caso de erro inesperado
        }
    }

    @GetMapping("/apuracao")
    public ResponseEntity<Apuracao> realizarApuracao() {
        try {
            Apuracao apuracao = votoService.realizarApuracao();

            return ResponseEntity.ok(apuracao);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}