package com.example.agendamento_service.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Objeto de transferência para requisições de exames e procedimentos entre os serviços")
public class PayloadExameDTO {

    @Schema(description = "Vínculo com o ID da consulta que originou o exame", example = "50")
    private Long consultaId;

    @Schema(description = "Documento de identificação do paciente", example = "123.456.789-10")
    private String cpfPaciente;

    @Schema(description = "Nome ou código do procedimento/exame solicitado", example = "Tomografia Computadorizada")
    private String tipoExame;

    @Schema(description = "Data e hora agendada para a realização", example = "2025-12-30T08:00:00")
    private LocalDateTime horario;

    @Schema(description = "Nível de urgência (Padrao ou Emergencial). Determina a regra de sobreposição de horário.", example = "Emergencial")
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
