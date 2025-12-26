package com.centro_laboratorio.controller;

import com.centro_laboratorio.domain.dto.ExameRequestDTO;
import com.centro_laboratorio.domain.dto.ExameResponseDTO;
import com.centro_laboratorio.service.ExameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exames")
@RequiredArgsConstructor
@Validated
public class ExameController {

    private final ExameService service;

    @PostMapping
    public ResponseEntity<ExameResponseDTO> criar(@Valid @RequestBody ExameRequestDTO dto) {
        ExameResponseDTO response = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExameResponseDTO>> listarTodos() {
        // Se a lista estiver vazia, o problema pode ser no Service/Repository
        List<ExameResponseDTO> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExameResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ExameRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}