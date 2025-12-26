package com.example.agendamento_service.repository;

import com.example.agendamento_service.domain.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    boolean existsByPacienteCpfAndHorario(String cpf, LocalDateTime horario);


}
