package com.centro_laboratorio.mapper;

import com.centro_laboratorio.domain.dto.ExameRequestDTO;
import com.centro_laboratorio.domain.dto.ExameResponseDTO;
import com.centro_laboratorio.domain.entity.Exame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExameMapper {
    Exame toEntity(ExameRequestDTO dto);

    @Mapping(target = "id", source = "id")
    ExameResponseDTO toResponse(Exame entity);

    List<ExameResponseDTO> toResponseList(List<Exame> entities);

    void updateEntityFromDto(ExameRequestDTO dto, @MappingTarget Exame entity);
}
