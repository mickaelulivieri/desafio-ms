package com.centro_laboratorio.mapper;

import com.centro_laboratorio.domain.dto.ProcedimentoRequestDTO;
import com.centro_laboratorio.domain.dto.ProcedimentoResponseDTO;
import com.centro_laboratorio.domain.entity.Procedimento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcedimentoMapper {
    Procedimento toEntity(ProcedimentoRequestDTO dto);

    ProcedimentoResponseDTO toResponse(Procedimento entity);

    List<ProcedimentoResponseDTO> toResponseList(List<Procedimento> entities);

    void updateEntityFromDto(ProcedimentoRequestDTO dto, @MappingTarget Procedimento entity);
}