package com.clinica_service.controller;

import com.clinica_service.domain.dto.AtendimentoRequestDTO;
import com.clinica_service.domain.dto.AtendimentoResponseDTO;
import com.clinica_service.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clinica")
@RequiredArgsConstructor
@Tag(name = "Atendimento Clínico", description = "Endpoints para realização de consultas e diagnósticos médicos")
public class AtendimentoController {

    private final ConsultaService consultaService;

    @Operation(
            summary = "Realizar atendimento de consulta",
            description = "Processa o atendimento de uma consulta agendada. " +
                    "Com base nos sintomas informados, o sistema sugere possíveis doenças e, " +
                    "em casos de alta complexidade (como suspeita de hemorragia ou tumor), " +
                    "gera automaticamente uma requisição de exame para o Laboratório."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Atendimento realizado com sucesso. Retorna o diagnóstico e código de exames se houver encaminhamento."
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida: CPF, horário ou lista de sintomas ausentes"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado para o CPF e horário informados")
    })
    @PostMapping("/AtenderConsulta")
    public ResponseEntity<AtendimentoResponseDTO> atender(@RequestBody AtendimentoRequestDTO request) {

        // Validação de dados obrigatórios conforme requisito [cite: 141, 142]
        if (request.getCpfPaciente() == null || request.getHorario() == null || request.getSintomas() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(consultaService.atender(request));
    }
}
