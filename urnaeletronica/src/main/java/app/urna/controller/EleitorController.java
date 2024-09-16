package app.urna.controller;

import app.urna.entity.Eleitor;
import app.urna.service.EleitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eleitores")
public class EleitorController {

    @Autowired
    private EleitorService eleitorService;

    // Endpoint para inativar um eleitor
    @PutMapping("/{id}/inativar")
    public ResponseEntity<Void> inativarEleitor(@PathVariable Long id) {
        eleitorService.inativarEleitor(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // Endpoint para salvar um novo eleitor
    @PostMapping
    public ResponseEntity<Eleitor> salvarEleitor(@RequestBody Eleitor eleitor) {
        Eleitor novoEleitor = eleitorService.salvarEleitor(eleitor);
        return ResponseEntity.ok(novoEleitor); // Retorna 200 OK com o eleitor salvo
    }

    // Endpoint para atualizar um eleitor existente
    @PutMapping("/{id}")
    public ResponseEntity<Eleitor> atualizarEleitor(@PathVariable Long id, @RequestBody Eleitor eleitorAtualizado) {
        Eleitor eleitorAtualizadoResp = eleitorService.atualizarEleitor(id, eleitorAtualizado);
        return ResponseEntity.ok(eleitorAtualizadoResp); // Retorna 200 OK com o eleitor atualizado
    }

    // Endpoint para listar todos os eleitores com status APTO
    @GetMapping
    public ResponseEntity<List<Eleitor>> listarEleitores() {
        List<Eleitor> eleitoresAptos = eleitorService.listarEleitores();
        return ResponseEntity.ok(eleitoresAptos); // Retorna 200 OK com a lista de eleitores aptos
    }
}
