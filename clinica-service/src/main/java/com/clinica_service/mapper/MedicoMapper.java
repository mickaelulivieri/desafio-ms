package com.clinica_service.mapper;

import com.clinica_service.domain.dto.MedicoRequestDTO;
import com.clinica_service.domain.dto.MedicoResponseDTO;
import com.clinica_service.domain.entity.Medico;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedicoMapper {

    Medico toEntity(MedicoRequestDTO dto);

    MedicoResponseDTO toResponse(Medico medico);

    void updateEntityFromDto(
            MedicoRequestDTO dto,
            @MappingTarget Medico medico
    );
}
