package com.clinica_service.controller;

import com.clinica_service.domain.dto.regranegocio.PayloadConsultaDTO;
import com.clinica_service.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clinica")
@RequiredArgsConstructor
@Tag(name = "Clínica - Validação", description = "Endpoints internos para validação de regras de negócio da clínica")
public class ClinicaController {

    private final ConsultaService consultaService;

    @Operation(
            summary = "Verificar disponibilidade do médico",
            description = "Endpoint utilizado pela API de Agendamento para verificar se um médico da especialidade informada já possui consulta no horário solicitado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico disponível para o horário"),
            @ApiResponse(responseCode = "409", description = "Conflito: O médico já possui agendamento neste horário")
    })
    @PostMapping("/verificar-disponibilidade")
    public ResponseEntity<Void> verificar(@RequestBody PayloadConsultaDTO payload) {

        consultaService.verificarDisponibilidade(
                payload.getEspecialidadeMedico(),
                payload.getHorario()
        );

        return ResponseEntity.ok().build();
    }
}