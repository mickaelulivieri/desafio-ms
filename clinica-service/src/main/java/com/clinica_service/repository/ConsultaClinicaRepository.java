package com.clinica_service.repository;

import com.clinica_service.domain.entity.ConsultaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ConsultaClinicaRepository
        extends JpaRepository<ConsultaClinica, Long> {

    boolean existsByEspecialidadeAndHorario(
            String especialidade,
            LocalDateTime horario
    );

    Optional<ConsultaClinica> findByCpfPacienteAndHorario(
            String cpfPaciente,
            LocalDateTime horario
    );
}
