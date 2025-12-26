package com.centro_laboratorio.controller;

import com.centro_laboratorio.service.DisponibilidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/disponibilidade")
@RequiredArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService service;

    @GetMapping("/validar")
    public ResponseEntity<Void> validar(
            @RequestParam LocalDateTime horario,
            @RequestParam String prioridade) {
        service.verificar(horario, prioridade);
        return ResponseEntity.ok().build();
    }
}
