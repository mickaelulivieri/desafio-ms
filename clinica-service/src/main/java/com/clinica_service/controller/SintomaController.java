package com.clinica_service.controller;

import com.clinica_service.domain.dto.SintomaRequestDTO;
import com.clinica_service.domain.dto.SintomaResponseDTO;
import com.clinica_service.service.SintomaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sintomas")
@RequiredArgsConstructor
public class SintomaController {

    private final SintomaService sintomaService;

    @PostMapping
    public ResponseEntity<SintomaResponseDTO> criar(@RequestBody SintomaRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sintomaService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SintomaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody SintomaRequestDTO dto
    ) {
        return ResponseEntity.ok(
                sintomaService.atualizar(id, dto)
        );
    }

    @GetMapping
    public ResponseEntity<List<SintomaResponseDTO>> listar() {
        return ResponseEntity.ok(
                sintomaService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SintomaResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                sintomaService.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {
        sintomaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
