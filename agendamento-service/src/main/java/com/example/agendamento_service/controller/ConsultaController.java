package com.example.agendamento_service.controller;

import com.example.agendamento_service.domain.dto.ConsultaRequestDTO;
import com.example.agendamento_service.domain.dto.ConsultaResponseDTO;
import com.example.agendamento_service.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponseDTO criar(@RequestBody ConsultaRequestDTO request) {
        return service.criar(request);
    }

    @GetMapping("/{id}")
    public ConsultaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<ConsultaResponseDTO> listar() {
        return service.listarTodas();
    }

    @PutMapping("/{id}")
    public ConsultaResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody ConsultaRequestDTO request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}

