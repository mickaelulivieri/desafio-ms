package com.clinica_service.domain.dto.regranegocio;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO de saída da Clínica para solicitação de exames ao Centro Médico/Laboratório")
public class PayloadExameDTO {

    @Schema(description = "Vínculo com o ID da consulta realizada na clínica", example = "305")
    private Long consultaId;

    @Schema(description = "CPF do paciente que recebeu o encaminhamento", example = "123.456.789-10")
    private String cpfPaciente;

    @Schema(description = "Descrição do exame solicitado pelo médico (ex: Tomografia, Sangue)", example = "Ressonância Magnética")
    private String tipoExame;

    @Schema(description = "Data e hora sugerida para a realização do procedimento", example = "2025-12-30T10:00:00")
    private LocalDateTime horario;

    @Schema(description = "Prioridade definida pelo médico (Normal ou Emergencial)", example = "Emergencial")
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