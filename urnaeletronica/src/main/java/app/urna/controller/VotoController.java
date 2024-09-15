package app.urna.controller;

import app.urna.entity.Apuracao;
import app.urna.entity.Voto;
import app.urna.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping("/votar/{idEleitor}")
    public ResponseEntity<String> votar(@RequestBody Voto voto, @PathVariable Long idEleitor) {
        String hash = votoService.votar(voto, idEleitor);
        return ResponseEntity.ok(hash);
    }

    @GetMapping("/apuracao")
    public ResponseEntity<Apuracao> realizarApuracao() {
        Apuracao apuracao = votoService.realizarApuracao();
        return ResponseEntity.ok(apuracao);
    }
}
