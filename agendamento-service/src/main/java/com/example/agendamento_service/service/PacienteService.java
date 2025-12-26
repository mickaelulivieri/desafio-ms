package com.example.agendamento_service.service;

import com.example.agendamento_service.domain.dto.PacienteRequestDTO;
import com.example.agendamento_service.domain.dto.PacienteResponseDTO;
import com.example.agendamento_service.domain.entity.Paciente;
import com.example.agendamento_service.exception.ResourceNotFoundException;
import com.example.agendamento_service.mapper.PacienteMapper;
import com.example.agendamento_service.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository repository;
    private final PacienteMapper mapper;

    public PacienteResponseDTO criar(PacienteRequestDTO request) {
        Paciente paciente = mapper.toEntity(request);
        return mapper.toResponse(repository.save(paciente));
    }

    public List<PacienteResponseDTO> listarTodos() {
        return mapper.toResponseList(repository.findAll());
    }

    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente não encontrado")
                );
        return mapper.toResponse(paciente);
    }

    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO request) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente não encontrado")
                );

        mapper.updateEntity(request, paciente);
        return mapper.toResponse(repository.save(paciente));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Paciente não encontrado");
        }
        repository.deleteById(id);
    }
}

