package com.clinica_service.controller;

import com.clinica_service.domain.dto.MedicoRequestDTO;
import com.clinica_service.domain.dto.MedicoResponseDTO;
import com.clinica_service.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
@Tag(name = "Médicos", description = "Gerenciamento do corpo clínico e especialidades do hospital")
public class MedicoController {

    private final MedicoService medicoService;

    @Operation(
            summary = "Cadastrar um novo médico",
            description = "Registra um profissional no banco de dados com seu nome, CRM e especialidade. " +
                    "Recurso exclusivo para usuários com a Role ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados obrigatórios inválidos ou CRM duplicado")
    })
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> criar(
            @RequestBody MedicoRequestDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(medicoService.criar(dto));
    }

    @Operation(summary = "Buscar médico por ID", description = "Retorna os detalhes de um médico específico cadastrado na clínica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado"),
            @ApiResponse(responseCode = "404", description = "Médico não localizado no sistema")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(medicoService.buscarPorId(id));
    }

    @Operation(summary = "Listar todos os médicos", description = "Retorna a lista completa de médicos ativos no hospital.")
    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(medicoService.listarTodos());
    }

    @Operation(summary = "Atualizar informações do médico", description = "Permite alterar os dados cadastrais de um médico existente. Recurso restrito ao ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado para a atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody MedicoRequestDTO dto
    ) {
        return ResponseEntity.ok(medicoService.atualizar(id, dto));
    }

    @Operation(summary = "Remover médico do sistema", description = "Exclui permanentemente o registro de um médico. Recurso exclusivo do ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Médico removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {
        medicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
