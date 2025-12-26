package com.centro_laboratorio.service;

import com.centro_laboratorio.domain.dto.ProcedimentoRequestDTO;
import com.centro_laboratorio.domain.dto.ProcedimentoResponseDTO;
import com.centro_laboratorio.domain.entity.Procedimento;
import com.centro_laboratorio.mapper.ProcedimentoMapper;
import com.centro_laboratorio.repository.ProcedimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcedimentoService {

    private final ProcedimentoRepository repository;
    private final ProcedimentoMapper mapper;
    private final DisponibilidadeService disponibilidadeService;


    public List<ProcedimentoResponseDTO> listarTodos() {
        return mapper.toResponseList(repository.findAll());
    }


    public ProcedimentoResponseDTO buscarPorId(Long id) {
        Procedimento entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procedimento (Código " + id + ") não encontrado."));
        return mapper.toResponse(entidade);
    }


    @Transactional
    public ProcedimentoResponseDTO marcarProcedimento(ProcedimentoRequestDTO dto) {
        log.info("Iniciando marcação para o procedimento ID: {}", dto.getId());

        Procedimento procedimento = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Código de procedimento inválido ou inexistente."));

        disponibilidadeService.verificar(dto.getHorario(), procedimento.getPrioridade());

        procedimento.setHorario(dto.getHorario());

        log.info("Procedimento ID {} marcado com sucesso para {}", procedimento.getId(), dto.getHorario());
        return mapper.toResponse(repository.save(procedimento));
    }

    public ProcedimentoResponseDTO criar(ProcedimentoRequestDTO dto) {
        disponibilidadeService.verificar(dto.getHorario(), dto.getPrioridade());
        Procedimento entidade = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entidade));
    }

    @Transactional
    public ProcedimentoResponseDTO atualizar(Long id, ProcedimentoRequestDTO dto) {
        Procedimento entidade = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));

        mapper.updateEntityFromDto(dto, entidade);
        return mapper.toResponse(repository.save(entidade));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Procedimento não encontrado.");
        }
        repository.deleteById(id);
    }
}
