package com.example.agendamento_service.mapper;

import com.example.agendamento_service.domain.dto.PacienteRequestDTO;
import com.example.agendamento_service.domain.dto.PacienteResponseDTO;
import com.example.agendamento_service.domain.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    Paciente toEntity(PacienteRequestDTO request);

    PacienteResponseDTO toResponse(Paciente paciente);

    List<PacienteResponseDTO> toResponseList(List<Paciente> pacientes);

    void updateEntity(PacienteRequestDTO request, @MappingTarget Paciente paciente);
}

