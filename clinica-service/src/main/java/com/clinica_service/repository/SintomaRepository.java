package com.clinica_service.repository;

import com.clinica_service.domain.entity.Sintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SintomaRepository extends JpaRepository<Sintoma, Long> {

    Optional<Sintoma> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}

