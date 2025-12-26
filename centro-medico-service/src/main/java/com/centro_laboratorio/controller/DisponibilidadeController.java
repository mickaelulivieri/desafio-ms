package com.centro_laboratorio.controller;

import com.centro_laboratorio.service.DisponibilidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/disponibilidade")
@RequiredArgsConstructor
@Tag(name = "Disponibilidade", description = "Validação de horários e recursos do laboratório")
public class DisponibilidadeController {

    private final DisponibilidadeService service;

    @Operation(
            summary = "Validar disponibilidade de horário",
            description = "Verifica se um horário está ocupado por outro exame ou procedimento. " +
                    "Regra Especial: Procedimentos 'Emergenciais' podem ser marcados em qualquer horário, " +
                    "exceto se já houver outro procedimento emergencial no mesmo momento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horário disponível para o nível de prioridade informado"),
            @ApiResponse(responseCode = "409", description = "Conflito: O horário já está ocupado por um agendamento incompatível")
    })
    @GetMapping("/validar")
    public ResponseEntity<Void> validar(
            @Parameter(description = "Data e hora desejada (ISO format)", example = "2025-12-30T10:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime horario,

            @Parameter(description = "Nível de prioridade (ex: Baixa, Padrão, Alta, Emergencial)", example = "Emergencial")
            @RequestParam String prioridade) {

        service.verificar(horario, prioridade);
        return ResponseEntity.ok().build();
    }
}
