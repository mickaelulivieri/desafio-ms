package com.clinica_service.controller;

import com.clinica_service.domain.dto.AtendimentoRequestDTO;
import com.clinica_service.domain.dto.AtendimentoResponseDTO;
import com.clinica_service.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clinica")
@RequiredArgsConstructor
public class AtendimentoController {

    private final ConsultaService consultaService;

    @PostMapping("/AtenderConsulta")
    public ResponseEntity<AtendimentoResponseDTO> atender(@RequestBody AtendimentoRequestDTO request) {

        if (request.getCpfPaciente() == null || request.getHorario() == null || request.getSintomas() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(consultaService.atender(request));
    }
}
