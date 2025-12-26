package com.clinica_service.mapper;

import com.clinica_service.domain.dto.SintomaRequestDTO;
import com.clinica_service.domain.dto.SintomaResponseDTO;
import com.clinica_service.domain.entity.Sintoma;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SintomaMapper {

    @Mapping(target = "prioridade", ignore = true)
    Sintoma toEntity(SintomaRequestDTO dto);

    SintomaResponseDTO toResponse(Sintoma entity);

    @Mapping(target = "prioridade", ignore = true)
    void updateEntityFromDto(
            SintomaRequestDTO dto,
            @MappingTarget Sintoma entity
    );
}
