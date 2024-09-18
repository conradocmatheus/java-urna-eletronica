package app.urna.controller;

import app.urna.entity.Eleitor;
import app.urna.handler.exception.BussinessException;
import app.urna.handler.exception.NotFoundException;
import app.urna.service.EleitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/eleitores")
public class EleitorController {

    @Autowired
    private EleitorService eleitorService;


    @PutMapping("/inativar/{id}")
    public ResponseEntity<String> inativarEleitor(@PathVariable Long id) {
        try {
            eleitorService.inativarEleitor(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eleitor inativado com sucesso");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BussinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao inativar eleitor");
        }
    }


    @PostMapping
    public ResponseEntity<Eleitor> salvarEleitor(@RequestBody @Valid Eleitor novoEleitor) {
        try {
            Eleitor eleitorSalvo = eleitorService.salvarEleitor(novoEleitor);
            return ResponseEntity.status(HttpStatus.CREATED).body(eleitorSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Eleitor> atualizarEleitor(
            @PathVariable Long id,
            @RequestBody @Valid Eleitor eleitorAtualizado) {
        try {
            Eleitor eleitor = eleitorService.atualizarEleitor(id, eleitorAtualizado);
            return ResponseEntity.status(HttpStatus.OK).body(eleitor);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/aptos")
    public ResponseEntity<List<Eleitor>> listarEleitoresAptos() {
        try {
            List<Eleitor> eleitores = eleitorService.listarEleitores();
            return ResponseEntity.status(HttpStatus.OK).body(eleitores);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
