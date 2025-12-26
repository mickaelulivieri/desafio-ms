package com.example.agendamento_service.mapper;

import com.example.agendamento_service.domain.dto.ConsultaRequestDTO;
import com.example.agendamento_service.domain.dto.ConsultaResponseDTO;
import com.example.agendamento_service.domain.entity.Consulta;
import com.example.agendamento_service.domain.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultaMapper {

    @Mapping(target = "paciente", source = "pacienteId")
    Consulta toEntity(ConsultaRequestDTO request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    ConsultaResponseDTO toResponse(Consulta consulta);

    List<ConsultaResponseDTO> toResponseList(List<Consulta> consultas);

    void updateEntity(ConsultaRequestDTO request, @MappingTarget Consulta consulta);

    default Paciente map(Long pacienteId) {
        if (pacienteId == null) return null;
        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);
        return paciente;
    }
}

