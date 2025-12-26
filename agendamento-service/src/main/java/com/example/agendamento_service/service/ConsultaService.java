package com.example.agendamento_service.service;

import com.example.agendamento_service.domain.dto.ConsultaRequestDTO;
import com.example.agendamento_service.domain.dto.ConsultaResponseDTO;
import com.example.agendamento_service.domain.dto.PayloadConsultaDTO;
import com.example.agendamento_service.domain.entity.Consulta;
import com.example.agendamento_service.domain.entity.Paciente;
import com.example.agendamento_service.exception.ResourceConflictException;
import com.example.agendamento_service.exception.ResourceNotFoundException;
import com.example.agendamento_service.mapper.ConsultaMapper;
import com.example.agendamento_service.producer.EventProducer;
import com.example.agendamento_service.repository.ConsultaRepository;
import com.example.agendamento_service.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository repository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaMapper mapper;
    private final EventProducer eventProducer;

    public ConsultaResponseDTO criar(ConsultaRequestDTO request) {

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado"));

        if (repository.existsByPacienteCpfAndHorario(paciente.getCpf(), request.getHorario())) {
            throw new ResourceConflictException(
                    "O paciente já possui agendamento no dia e horário " + request.getHorario()
            );
        }

        Consulta consulta = mapper.toEntity(request);
        consulta.setPaciente(paciente);
        consulta = repository.save(consulta);

        PayloadConsultaDTO payload = new PayloadConsultaDTO();
        payload.setId(consulta.getId());
        payload.setCpfPaciente(paciente.getCpf());
        payload.setHorario(consulta.getHorario());
        payload.setEspecialidadeMedico(consulta.getEspecialidade());

        eventProducer.sendConsultaCreated(payload);

        return mapper.toResponse(consulta);
    }

    public List<ConsultaResponseDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ConsultaResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
    }

    public ConsultaResponseDTO atualizar(Long id, ConsultaRequestDTO request) {

        Consulta consulta = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Consulta não encontrada")
                );

        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paciente não encontrado")
                );

        mapper.updateEntity(request, consulta);
        consulta.setPaciente(paciente);

        consulta = repository.save(consulta);

        PayloadConsultaDTO payload = new PayloadConsultaDTO();
        payload.setId(consulta.getId());
        payload.setCpfPaciente(paciente.getCpf());
        payload.setHorario(consulta.getHorario());
        payload.setEspecialidadeMedico(consulta.getEspecialidade());

        eventProducer.sendConsultaUpdated(payload);

        return mapper.toResponse(consulta);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Consulta não encontrada");
        }
        repository.deleteById(id);
    }
}
