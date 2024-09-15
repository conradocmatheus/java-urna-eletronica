package app.urna.controller;

import app.urna.entity.Candidato;
import app.urna.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoService candidatoService;

    // Inativar candidato
    @PutMapping("/{id}/inativar")
    public ResponseEntity<Void> inativarCandidato(@PathVariable Long id) {
        candidatoService.inativarCandidato(id);
        return ResponseEntity.noContent().build();
    }

    // Salvar candidato
    @PostMapping
    public ResponseEntity<Candidato> salvarCandidato(@RequestBody Candidato candidato) {
        Candidato candidatoSalvo = candidatoService.salvarCandidato(candidato);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidatoSalvo);
    }

    // Atualizar candidato
    @PutMapping("/{id}")
    public ResponseEntity<Candidato> atualizarCandidato(@PathVariable Long id, @RequestBody Candidato candidatoAtualizado) {
        Candidato candidatoAtualizadoSalvo = candidatoService.atualizarCandidato(id, candidatoAtualizado);
        return ResponseEntity.ok(candidatoAtualizadoSalvo);
    }

    // Listar candidatos ativos
    @GetMapping
    public ResponseEntity<List<Candidato>> listarCandidatos() {
        List<Candidato> candidatosAtivos = candidatoService.listarCandidatos();
        return ResponseEntity.ok(candidatosAtivos);
    }

    // Listar candidatos a vereador ativos
    @GetMapping("/vereadores")
    public ResponseEntity<List<Candidato>> listarCandidatosVereadoresAtivos() {
        List<Candidato> vereadoresAtivos = candidatoService.listarCandidatosVereadoresAtivos();
        return ResponseEntity.ok(vereadoresAtivos);
    }

    // Listar candidatos a prefeito ativos
    @GetMapping("/prefeitos")
    public ResponseEntity<List<Candidato>> listarCandidatosPrefeitosAtivos() {
        List<Candidato> prefeitosAtivos = candidatoService.listarCandidatosPrefeitosAtivos();
        return ResponseEntity.ok(prefeitosAtivos);
    }
}
