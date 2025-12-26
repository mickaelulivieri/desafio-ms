package com.clinica_service.service;

import com.clinica_service.domain.dto.MedicoRequestDTO;
import com.clinica_service.domain.dto.MedicoResponseDTO;
import com.clinica_service.domain.entity.Medico;
import com.clinica_service.exception.ResourceNotFoundException;
import com.clinica_service.mapper.MedicoMapper;
import com.clinica_service.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final MedicoMapper medicoMapper;

    public MedicoResponseDTO criar(MedicoRequestDTO dto) {
        Medico medico = medicoMapper.toEntity(dto);
        medico = medicoRepository.save(medico);
        return medicoMapper.toResponse(medico);
    }

    public MedicoResponseDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Médico não encontrado"));
        return medicoMapper.toResponse(medico);
    }

    public List<MedicoResponseDTO> listarTodos() {
        return medicoRepository.findAll()
                .stream()
                .map(medicoMapper::toResponse)
                .toList();
    }

    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO dto) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Médico não encontrado")
                );

        medicoMapper.updateEntityFromDto(dto, medico);
        medico = medicoRepository.save(medico);

        return medicoMapper.toResponse(medico);
    }

    public void deletar(Long id) {
        if (!medicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Médico não encontrado");
        }
        medicoRepository.deleteById(id);
    }
}
