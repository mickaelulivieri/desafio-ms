package com.centro_laboratorio.controller;

import com.centro_laboratorio.domain.dto.ProcedimentoRequestDTO;
import com.centro_laboratorio.domain.dto.ProcedimentoResponseDTO;
import com.centro_laboratorio.service.ProcedimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/procedimentos")
@RequiredArgsConstructor
@Tag(name = "Procedimentos", description = "Gerenciamento de procedimentos de alta complexidade e cirurgias")
public class ProcedimentoController {

    private final ProcedimentoService service;

    @Operation(
            summary = "Marcar ou atualizar horário de procedimento",
            description = "Permite agendar um horário para um procedimento de alta complexidade previamente criado. " +
                    "Regra Especial: Se marcado como 'Emergencial', o agendamento é permitido em qualquer horário, " +
                    "mesmo que ocupado, exceto se houver outro emergencial no mesmo momento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento marcado ou atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados obrigatórios ausentes ou ID de procedimento inválido"),
            @ApiResponse(responseCode = "409", description = "Conflito: Horário ocupado por outro procedimento não sobreponível")
    })
    @PostMapping("/marcar")
    public ResponseEntity<ProcedimentoResponseDTO> marcar(@RequestBody ProcedimentoRequestDTO dto) {
        return ResponseEntity.ok(service.marcarProcedimento(dto));
    }

    @Operation(summary = "Listar todos os procedimentos", description = "Retorna a lista de todos os procedimentos e cirurgias registrados.")
    @GetMapping
    public ResponseEntity<List<ProcedimentoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar procedimento por ID", description = "Recupera os detalhes de um procedimento específico através de seu código.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procedimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Código de procedimento inexistente")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProcedimentoResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Excluir procedimento", description = "Remove um procedimento do banco de dados do laboratório.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Procedimento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Procedimento não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}