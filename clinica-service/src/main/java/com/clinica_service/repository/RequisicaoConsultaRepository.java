package com.clinica_service.repository;

import com.clinica_service.domain.entity.RequisicaoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RequisicaoConsultaRepository
        extends JpaRepository<RequisicaoConsulta, Long> {

    Optional<RequisicaoConsulta>
    findByCpfPacienteAndHorario(String cpfPaciente, LocalDateTime horario);

    boolean existsByConsultaId(Long consultaId);

}
