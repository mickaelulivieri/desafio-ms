package com.example.agendamento_service.mapper;

import com.example.agendamento_service.domain.dto.ExameRequestDTO;
import com.example.agendamento_service.domain.dto.ExameResponseDTO;
import com.example.agendamento_service.domain.entity.Exame;
import com.example.agendamento_service.domain.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExameMapper {

    @Mapping(target = "paciente", source = "pacienteId")
    Exame toEntity(ExameRequestDTO request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    ExameResponseDTO toResponse(Exame exame);

    List<ExameResponseDTO> toResponseList(List<Exame> exames);

    void updateEntity(ExameRequestDTO request, @MappingTarget Exame exame);

    default Paciente map(Long pacienteId) {
        if (pacienteId == null) return null;
        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);
        return paciente;
    }
}

