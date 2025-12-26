package com.centro_laboratorio.controller;

import com.centro_laboratorio.domain.dto.ProcedimentoRequestDTO;
import com.centro_laboratorio.domain.dto.ProcedimentoResponseDTO;
import com.centro_laboratorio.service.ProcedimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/procedimentos")
@RequiredArgsConstructor
public class ProcedimentoController {

    private final ProcedimentoService service;

    @PostMapping("/marcar")
    public ResponseEntity<ProcedimentoResponseDTO> marcar(@RequestBody ProcedimentoRequestDTO dto) {
        return ResponseEntity.ok(service.marcarProcedimento(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProcedimentoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedimentoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
