package com.clinica_service.controller;

import com.clinica_service.domain.dto.regranegocio.PayloadConsultaDTO;
import com.clinica_service.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clinica")
@RequiredArgsConstructor
public class ClinicaController {

    private final ConsultaService consultaService;

    @PostMapping("/verificar-disponibilidade")
    public ResponseEntity<Void> verificar(@RequestBody PayloadConsultaDTO payload) {

        consultaService.verificarDisponibilidade(
                payload.getEspecialidadeMedico(),
                payload.getHorario()
        );

        return ResponseEntity.ok().build();
    }
}
