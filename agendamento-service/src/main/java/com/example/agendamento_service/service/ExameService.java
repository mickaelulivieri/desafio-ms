package com.example.agendamento_service.service;

import com.example.agendamento_service.domain.dto.ExameRequestDTO;
import com.example.agendamento_service.domain.dto.ExameResponseDTO;
import com.example.agendamento_service.domain.dto.PayloadExameDTO;
import com.example.agendamento_service.domain.entity.Exame;
import com.example.agendamento_service.domain.entity.Paciente;
import com.example.agendamento_service.exception.ResourceConflictException;
import com.example.agendamento_service.exception.ResourceNotFoundException;
import com.example.agendamento_service.mapper.ExameMapper;
import com.example.agendamento_service.producer.EventProducer;
import com.example.agendamento_service.repository.ExameRepository;
import com.example.agendamento_service.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExameService {

    private final ExameRepository repository;
    private final PacienteRepository pacienteRepository;
    private final ExameMapper mapper;
    private final EventProducer eventProducer;

    public ExameResponseDTO criar(ExameRequestDTO request) {

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        if (repository.existsByPacienteCpfAndHorario(paciente.getCpf(), request.getHorario())) {
            throw new ResourceConflictException(
                    "O paciente já possui agendamento no dia e horário " + request.getHorario()
            );
        }

        Exame exame = mapper.toEntity(request);
        exame.setPaciente(paciente);
        exame = repository.save(exame);

        PayloadExameDTO payload = new PayloadExameDTO();
        payload.setConsultaId(exame.getId());
        payload.setCpfPaciente(paciente.getCpf());
        payload.setHorario(exame.getHorario());
        payload.setTipoExame(exame.getTipoExame());

        eventProducer.sendExameCreated(payload);

        return mapper.toResponse(exame);
    }

    public List<ExameResponseDTO> listarTodos() {
        return mapper.toResponseList(repository.findAll());
    }

    public ExameResponseDTO buscarPorId(Long id) {
        Exame exame = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Exame não encontrado")
                );
        return mapper.toResponse(exame);
    }

    public ExameResponseDTO atualizar(Long id, ExameRequestDTO request) {

        Exame exame = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Exame não encontrado")
                );

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente não encontrado")
                );

        mapper.updateEntity(request, exame);
        exame.setPaciente(paciente);
        exame = repository.save(exame);

        PayloadExameDTO payload = new PayloadExameDTO();
        payload.setConsultaId(exame.getId());
        payload.setCpfPaciente(paciente.getCpf());
        payload.setHorario(exame.getHorario());
        payload.setTipoExame(exame.getTipoExame());

        eventProducer.sendExameUpdated(payload);

        return mapper.toResponse(exame);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Exame não encontrado");
        }
        repository.deleteById(id);
    }
}

