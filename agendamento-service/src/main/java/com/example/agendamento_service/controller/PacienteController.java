package com.example.agendamento_service.controller;

import com.example.agendamento_service.domain.dto.PacienteRequestDTO;
import com.example.agendamento_service.domain.dto.PacienteResponseDTO;
import com.example.agendamento_service.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponseDTO criar(@RequestBody PacienteRequestDTO request) {
        return service.criar(request);
    }

    @GetMapping
    public List<PacienteResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public PacienteResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public PacienteResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody PacienteRequestDTO request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
