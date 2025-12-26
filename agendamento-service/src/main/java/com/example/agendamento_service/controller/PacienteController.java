package com.example.agendamento_service.controller;

import com.example.agendamento_service.domain.dto.PacienteRequestDTO;
import com.example.agendamento_service.domain.dto.PacienteResponseDTO;
import com.example.agendamento_service.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
@Tag(name = "Pacientes", description = "Gerenciamento de dados cadastrais dos pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @Operation(summary = "Cadastrar um novo paciente",
            description = "Permite a criação de um novo registro de paciente no sistema. " +
                    "Recurso acessível por Usuários e Admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados obrigatórios inválidos ou ausentes")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponseDTO criar(@RequestBody PacienteRequestDTO request) {
        return service.criar(request);
    }

    @Operation(summary = "Listar todos os pacientes",
            description = "Retorna uma lista de todos os pacientes cadastrados. " +
                    "Recurso tipicamente restrito ao perfil Admin.")
    @GetMapping
    public List<PacienteResponseDTO> listar() {
        return service.listarTodos();
    }

    @Operation(summary = "Buscar paciente por ID",
            description = "Obtém os detalhes cadastrais de um paciente específico através do seu identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @GetMapping("/{id}")
    public PacienteResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Atualizar dados do paciente",
            description = "Permite editar as informações de um paciente existente. " +
                    "Acesso permitido para o próprio Usuário ou Admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado para atualização")
    })
    @PutMapping("/{id}")
    public PacienteResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody PacienteRequestDTO request) {
        return service.atualizar(id, request);
    }

    @Operation(summary = "Excluir cadastro de paciente",
            description = "Remove permanentemente o registro de um paciente do banco de dados. " +
                    "Recurso restrito ao perfil Admin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}