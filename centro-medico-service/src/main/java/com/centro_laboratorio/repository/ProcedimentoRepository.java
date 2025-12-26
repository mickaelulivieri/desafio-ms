package com.centro_laboratorio.repository;

import com.centro_laboratorio.domain.entity.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {

    Optional<Procedimento> findByHorario(LocalDateTime horario);
}
