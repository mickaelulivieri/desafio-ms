package com.centro_laboratorio.service;

import com.centro_laboratorio.exception.ConflictException;
import com.centro_laboratorio.repository.ExameRepository;
import com.centro_laboratorio.repository.ProcedimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final ExameRepository exameRepository;
    private final ProcedimentoRepository procedimentoRepository;

    public void verificar(LocalDateTime horario, String prioridade) {
        boolean ocupado = exameRepository.findByHorario(horario).isPresent() ||
                procedimentoRepository.findByHorario(horario).isPresent();

        if (ocupado) {

            throw new ConflictException("O horário " + horario + " já está ocupado no laboratório.");
        }
    }
}
