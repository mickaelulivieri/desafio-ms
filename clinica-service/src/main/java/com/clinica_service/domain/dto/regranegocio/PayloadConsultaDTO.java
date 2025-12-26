package com.clinica_service.domain.dto.regranegocio;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO de entrada para validação e registro de consultas na Clínica")
public class PayloadConsultaDTO {

    @Schema(description = "ID de referência da consulta original", example = "200")
    private Long id;

    @Schema(description = "CPF do paciente para verificação de histórico", example = "123.456.789-10")
    private String cpfPaciente;

    @Schema(description = "Data e hora pretendida para a consulta", example = "2025-12-30T15:00:00")
    private LocalDateTime horario;

    @Schema(description = "Especialidade médica a ser validada", example = "Ortopedia")
    private String especialidadeMedico;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getEspecialidadeMedico() {
        return especialidadeMedico;
    }

    public void setEspecialidadeMedico(String especialidadeMedico) {
        this.especialidadeMedico = especialidadeMedico;
    }
}