package com.example.agendamento_service.controller;

import com.example.agendamento_service.domain.dto.ConsultaRequestDTO;
import com.example.agendamento_service.domain.dto.ConsultaResponseDTO;
import com.example.agendamento_service.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Gerenciamento de agendamentos de consultas médicas")
public class ConsultaController {

    private final ConsultaService service;

    @Operation(summary = "Agendar uma nova consulta",
            description = "Registra uma consulta e envia os dados para a API de Clínica. " +
                    "Não permite agendamentos duplicados para o mesmo CPF no mesmo horário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta agendada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito: O paciente já possui agendamento neste horário")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponseDTO criar(@RequestBody ConsultaRequestDTO request) {
        return service.criar(request);
    }

    @Operation(summary = "Buscar consulta por ID", description = "Retorna os detalhes de um agendamento específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta encontrada"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    @GetMapping("/{id}")
    public ConsultaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Listar todas as consultas", description = "Retorna a lista completa de agendamentos registrados.")
    @GetMapping
    public List<ConsultaResponseDTO> listar() {
        return service.listarTodas();
    }

    @Operation(summary = "Atualizar um agendamento",
            description = "Modifica os dados de uma consulta existente e sincroniza com a API de Clínica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada para atualização")
    })
    @PutMapping("/{id}")
    public ConsultaResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody ConsultaRequestDTO request) {
        return service.atualizar(id, request);
    }

    @Operation(summary = "Remover uma consulta", description = "Exclui permanentemente um agendamento do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consulta removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}

