package app.urna.controller;

import app.urna.entity.Candidato;
import app.urna.handler.exception.NotFoundException;
import app.urna.service.CandidatoService;
import jakarta.validation.Valid;
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

    // Inativar candidato por ID
    @PutMapping("/inativar/{id}")
    public ResponseEntity<String> inativarCandidato(@PathVariable Long id) {
        try {
            candidatoService.inativarCandidato(id);
            return ResponseEntity.status(HttpStatus.OK).body("Candidato inativado com sucesso");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao inativar candidato");
        }
    }

    // Salvar candidato
    @PostMapping("/salvar")
    public ResponseEntity<Candidato> salvarCandidato(@RequestBody @Valid Candidato novoCandidato) {
        try {
            Candidato candidatoSalvo = candidatoService.salvarCandidato(novoCandidato);
            return ResponseEntity.status(HttpStatus.CREATED).body(candidatoSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Atualizar candidato
    @PutMapping("atualizar/{id}")
    public ResponseEntity<Candidato> atualizarCandidato(
            @PathVariable Long id,
            @RequestBody @Valid Candidato candidatoAtualizado) {
        try {
            Candidato candidato = candidatoService.atualizarCandidato(id, candidatoAtualizado);
            return ResponseEntity.status(HttpStatus.OK).body(candidato);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Lista todos os candidatos ATIVOS
    @GetMapping("/listar")
    public ResponseEntity<List<Candidato>> listarCandidatos() {
        try {
            List<Candidato> candidatos = candidatoService.listarCandidatos();
            return ResponseEntity.status(HttpStatus.OK).body(candidatos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Lista os candidatos a vereador ATIVOS
    @GetMapping("/listar/vereadores")
    public ResponseEntity<List<Candidato>> listarCandidatosVereadoresAtivos() {
        try {
            List<Candidato> candidatosVereadores = candidatoService.listarCandidatosVereadoresAtivos();
            return ResponseEntity.status(HttpStatus.OK).body(candidatosVereadores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Lista os candidatos a prefeito ATIVOS
    @GetMapping("/listar/prefeitos")
    public ResponseEntity<List<Candidato>> listarCandidatosPrefeitosAtivos() {
        try {
            List<Candidato> candidatosPrefeitos = candidatoService.listarCandidatosPrefeitosAtivos();
            return ResponseEntity.status(HttpStatus.OK).body(candidatosPrefeitos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
