package com.clinica_service.service;

import com.clinica_service.domain.dto.SintomaRequestDTO;
import com.clinica_service.domain.dto.SintomaResponseDTO;
import com.clinica_service.domain.entity.Sintoma;
import com.clinica_service.domain.enums.PrioridadeSintoma;
import com.clinica_service.exception.BadRequestException;
import com.clinica_service.exception.ConflictException;
import com.clinica_service.exception.ResourceNotFoundException;
import com.clinica_service.mapper.SintomaMapper;
import com.clinica_service.repository.SintomaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SintomaService {

    private final SintomaRepository sintomaRepository;
    private final SintomaMapper sintomaMapper;

    public SintomaResponseDTO criar(SintomaRequestDTO dto) {

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new BadRequestException("Nome do sintoma é obrigatório");
        }

        if (dto.getPrioridade() == null || dto.getPrioridade().isBlank()) {
            throw new BadRequestException("Prioridade é obrigatória");
        }

        PrioridadeSintoma prioridade;
        try {
            prioridade = PrioridadeSintoma.valueOf(dto.getPrioridade().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Prioridade inválida");
        }

        if (sintomaRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new ConflictException("Sintoma já cadastrado");
        }

        Sintoma sintoma = new Sintoma();
        sintoma.setNome(dto.getNome());
        sintoma.setDoencasRelacionadas(dto.getDoencasRelacionadas());
        sintoma.setPrioridade(prioridade);

        sintoma = sintomaRepository.save(sintoma);
        return sintomaMapper.toResponse(sintoma);
    }

    @Transactional
    public SintomaResponseDTO atualizar(Long id, SintomaRequestDTO dto) {

        Sintoma sintoma = sintomaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sintoma não encontrado"));

        sintomaMapper.updateEntityFromDto(dto, sintoma);

        sintoma = sintomaRepository.save(sintoma);
        return sintomaMapper.toResponse(sintoma);
    }

    @Transactional
    public List<SintomaResponseDTO> listarTodos() {
        return sintomaRepository.findAll()
                .stream()
                .map(sintomaMapper::toResponse)
                .toList();
    }

    @Transactional
    public SintomaResponseDTO buscarPorId(Long id) {
        Sintoma sintoma = sintomaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sintoma não encontrado"));
        return sintomaMapper.toResponse(sintoma);
    }

    @Transactional
    public void deletar(Long id) {
        if (!sintomaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sintoma não encontrado");
        }
        sintomaRepository.deleteById(id);
    }
}

