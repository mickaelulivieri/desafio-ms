package com.centro_laboratorio.repository;

import com.centro_laboratorio.domain.entity.Exame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ExameRepository extends JpaRepository<Exame, Long> {

    Optional<Exame> findByHorario(LocalDateTime horario);
}
