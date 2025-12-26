package com.centro_laboratorio.domain.dto.regranegocio;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO de entrada para o processamento de exames e procedimentos no Centro Laboratório via Mensageria")
public class PayloadExameDTO {

    @Schema(description = "Identificador da consulta de origem para rastreabilidade", example = "150")
    private Long consultaId;

    @Schema(description = "CPF do paciente para conferência de registro", example = "123.456.789-10")
    private String cpfPaciente;

    @Schema(description = "Tipo de procedimento (exame simples ou alta complexidade)", example = "Ressonância Magnética")
    private String tipoExame;

    @Schema(description = "Data e hora agendada para o procedimento", example = "2025-12-30T09:00:00")
    private LocalDateTime horario;

    @Schema(description = "Nível de prioridade (Emergencial permite sobreposição de horário)", example = "Emergencial")
    private String prioridade;

    // Getters e Setters

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
