package com.example.agendamento_service.repository;

import com.example.agendamento_service.domain.entity.Exame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ExameRepository extends JpaRepository<Exame, Long> {

    boolean existsByPacienteCpfAndHorario(String cpf, LocalDateTime horario);
}

