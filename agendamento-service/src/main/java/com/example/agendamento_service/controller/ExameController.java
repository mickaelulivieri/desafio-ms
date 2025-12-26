package com.example.agendamento_service.controller;

import com.example.agendamento_service.domain.dto.ExameRequestDTO;
import com.example.agendamento_service.domain.dto.ExameResponseDTO;
import com.example.agendamento_service.service.ExameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExameController {

    private final ExameService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExameResponseDTO criar(@RequestBody ExameRequestDTO request) {
        return service.criar(request);
    }

    @GetMapping
    public List<ExameResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ExameResponseDTO buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ExameResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody ExameRequestDTO request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}

