package com.clinica_service.controller;

import com.clinica_service.domain.dto.SintomaRequestDTO;
import com.clinica_service.domain.dto.SintomaResponseDTO;
import com.clinica_service.service.SintomaService;
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
@RequestMapping("/api/sintomas")
@RequiredArgsConstructor
@Tag(name = "Sintomas", description = "Gerenciamento do catálogo de sintomas e mapeamento de doenças")
public class SintomaController {

    private final SintomaService sintomaService;

    @Operation(
            summary = "Cadastrar um novo sintoma",
            description = "Registra um sintoma no sistema, associando-o a doenças relacionadas e definindo sua gravidade (Ex: Padrão, Alta, Emergencial). " +
                    "Estes dados são usados pelo médico para gerar diagnósticos automáticos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sintoma cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados obrigatórios inválidos")
    })
    @PostMapping
    public ResponseEntity<SintomaResponseDTO> criar(@RequestBody SintomaRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sintomaService.criar(dto));
    }

    @Operation(summary = "Atualizar informações do sintoma", description = "Permite alterar a prioridade ou as doenças vinculadas a um sintoma existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sintoma atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sintoma não encontrado para atualização")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SintomaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody SintomaRequestDTO dto
    ) {
        return ResponseEntity.ok(
                sintomaService.atualizar(id, dto)
        );
    }

    @Operation(summary = "Listar todos os sintomas", description = "Retorna a lista completa de sintomas cadastrados para consulta médica.")
    @GetMapping
    public ResponseEntity<List<SintomaResponseDTO>> listar() {
        return ResponseEntity.ok(
                sintomaService.listarTodos()
        );
    }

    @Operation(summary = "Buscar sintoma por ID", description = "Recupera os detalhes de um sintoma específico através de seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sintoma encontrado"),
            @ApiResponse(responseCode = "404", description = "Sintoma não localizado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SintomaResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                sintomaService.buscarPorId(id)
        );
    }

    @Operation(summary = "Remover sintoma", description = "Exclui um sintoma do catálogo. Ação restrita ao perfil ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sintoma removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sintoma não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id
    ) {
        sintomaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
