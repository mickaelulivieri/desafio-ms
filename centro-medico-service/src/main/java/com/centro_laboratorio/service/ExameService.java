package com.centro_laboratorio.service;

import com.centro_laboratorio.domain.dto.ExameRequestDTO;
import com.centro_laboratorio.domain.dto.ExameResponseDTO;
import com.centro_laboratorio.domain.entity.Exame;
import com.centro_laboratorio.mapper.ExameMapper;
import com.centro_laboratorio.repository.ExameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExameService {

    private final ExameRepository repository;
    private final ExameMapper mapper;

    public ExameResponseDTO criar(ExameRequestDTO dto) {
        Exame entidade = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entidade));
    }

    public List<ExameResponseDTO> listarTodos() {
        return mapper.toResponseList(repository.findAll());
    }

    public ExameResponseDTO buscarPorId(Long id) {
        Exame entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não encontrado"));
        return mapper.toResponse(entidade);
    }

    public ExameResponseDTO atualizar(Long id, ExameRequestDTO dto) {
        Exame entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exame não encontrado"));

        mapper.updateEntityFromDto(dto, entidade);
        return mapper.toResponse(repository.save(entidade));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
