package com.clinica_service.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_consulta_atendida")
public class ConsultaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agendamentoId;

    private String cpfPaciente;

    private String especialidade;

    private LocalDateTime horario;

    private String status;

    public ConsultaClinica(Long id, Long agendamentoId, String cpfPaciente, String especialidade, LocalDateTime horario, String status) {
        this.id = id;
        this.agendamentoId = agendamentoId;
        this.cpfPaciente = cpfPaciente;
        this.especialidade = especialidade;
        this.horario = horario;
        this.status = status;
    }

    public ConsultaClinica() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public Long getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(Long agendamentoId) {
        this.agendamentoId = agendamentoId;
    }
}