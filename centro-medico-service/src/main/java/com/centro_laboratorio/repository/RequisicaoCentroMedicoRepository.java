package com.centro_laboratorio.repository;

import com.centro_laboratorio.domain.entity.RequisicaoCentroMedico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequisicaoCentroMedicoRepository extends JpaRepository<RequisicaoCentroMedico, Long> {
    boolean existsByConsultaId(Long consultaId);

}
