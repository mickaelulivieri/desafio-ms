package com.clinica_service.controller;

import com.clinica_service.repository.ConsultaClinicaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clínica - Validação Interna", description = "Endpoints de suporte para validação de regras de negócio entre microsserviços")
public class ValidacaoConsultaController {

    private final ConsultaClinicaRepository repository;

    @Operation(
            summary = "Validar conflito de agenda médica",
            description = "Verifica se já existe uma consulta marcada para a especialidade informada no horário solicitado. " +
                    "Este endpoint é consumido pelo Agendamento-Service durante o fluxo de criação de consultas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horário disponível para a especialidade informada"),
            @ApiResponse(responseCode = "409", description = "Conflito: Já existe um médico desta especialidade ocupado neste horário")
    })
    @GetMapping("/consulta")
    public ResponseEntity<Void> validarHorario(
            @Parameter(description = "Nome da especialidade médica", example = "Pediatria")
            @RequestParam String especialidade,

            @Parameter(description = "Data e hora no formato ISO (yyyy-MM-ddTHH:mm:ss)", example = "2025-12-30T10:00:00")
            @RequestParam String horario
    ) {
        LocalDateTime data = LocalDateTime.parse(horario);

        boolean ocupado = repository
                .existsByEspecialidadeAndHorario(especialidade, data);

        if (ocupado) {
            // Retorna 409 conforme requisito de tratamento de conflitos
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok().build();
    }
}
