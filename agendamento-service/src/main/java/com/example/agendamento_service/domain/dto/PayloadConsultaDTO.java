package com.example.agendamento_service.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Objeto de transferência para sincronização de dados de consulta entre microsserviços")
public class PayloadConsultaDTO {

    @Schema(description = "Identificador único da consulta gerado pelo serviço de agendamento", example = "10")
    private Long id;

    @Schema(description = "CPF do paciente vinculado à consulta (com pontuação)", example = "123.456.789-10")
    private String cpfPaciente;

    @Schema(description = "Data e horário da consulta no formato ISO 8601", example = "2025-12-30T14:30:00")
    private LocalDateTime horario;

    @Schema(description = "Especialidade do médico requisitada para o atendimento", example = "Cardiologia")
    private String especialidadeMedico;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecialidadeMedico() {
        return especialidadeMedico;
    }

    public void setEspecialidadeMedico(String especialidadeMedico) {
        this.especialidadeMedico = especialidadeMedico;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }
}