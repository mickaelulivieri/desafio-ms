package com.clinica_service.controller;

import com.clinica_service.repository.ConsultaClinicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/clinica/validacao")
@RequiredArgsConstructor
public class ValidacaoConsultaController {

    private final ConsultaClinicaRepository repository;

    @GetMapping("/consulta")
    public ResponseEntity<Void> validarHorario(
            @RequestParam String especialidade,
            @RequestParam String horario
    ) {
        LocalDateTime data = LocalDateTime.parse(horario);

        boolean ocupado = repository
                .existsByEspecialidadeAndHorario(especialidade, data);

        if (ocupado) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok().build();
    }
}
