package com.example.agendamento_service.controller;

import com.example.agendamento_service.domain.dto.ExameRequestDTO;
import com.example.agendamento_service.domain.dto.ExameResponseDTO;
import com.example.agendamento_service.service.ExameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
@Tag(name = "Exames", description = "Gerenciamento de solicitações de exames laboratoriais e cirúrgicos")
public class ExameController {

    private final ExameService service;

    @Operation(
            summary = "Agendar um novo exame",
            description = "Cria um registro de exame no banco de dados  e envia automaticamente uma requisição para a API de Centro Cirúrgico/Laboratório."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exame agendado e enviado para o laboratório com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados obrigatórios ausentes ou inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito: Paciente já possui agendamento neste dia e hora ")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExameResponseDTO criar(@RequestBody ExameRequestDTO request) {
        return service.criar(request);
    }

    @Operation(summary = "Listar todos os exames", description = "Retorna todos os registros de exames do sistema de agendamento.")
    @GetMapping
    public List<ExameResponseDTO> listar() {
        return service.listarTodos();
    }

    @Operation(summary = "Buscar exame por ID", description = "Obtém os detalhes de uma solicitação de exame específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame encontrado"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    })
    @GetMapping("/{id}")
    public ExameResponseDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(
            summary = "Atualizar agendamento de exame",
            description = "Atualiza os dados do exame e sincroniza a mudança com o Centro Cirúrgico/Laboratório."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exame atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado para atualização")
    })
    @PutMapping("/{id}")
    public ExameResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody ExameRequestDTO request) {
        return service.atualizar(id, request);
    }

    @Operation(summary = "Deletar/Cancelar exame", description = "Remove o registro do agendamento de exame do banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exame removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Exame não encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}

