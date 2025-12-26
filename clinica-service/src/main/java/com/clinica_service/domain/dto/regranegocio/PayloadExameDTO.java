package com.clinica_service.domain.dto.regranegocio;

import java.time.LocalDateTime;

public class PayloadExameDTO {

        private Long consultaId;     // ID original do agendamento [cite: 195]
        private String cpfPaciente;  // CPF para vínculo no laboratório [cite: 159, 194]
        private String tipoExame;    // Tipo do procedimento/cirurgia [cite: 163]
        private LocalDateTime horario; // Dia e hora marcados [cite: 196]
        private String prioridade;

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
