package com.centro_laboratorio.domain.dto;

import java.time.LocalDateTime;

public class ExameRequestDTO {
    private String cpfPaciente;
    private String nomeExame;
    private String prioridade;
    private LocalDateTime horario;
    private boolean altaComplexidade;

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public boolean isAltaComplexidade() {
        return altaComplexidade;
    }

    public void setAltaComplexidade(boolean altaComplexidade) {
        this.altaComplexidade = altaComplexidade;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }
}
