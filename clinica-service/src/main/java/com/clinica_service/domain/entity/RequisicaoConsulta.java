package com.clinica_service.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "db_requisicao_consulta")
public class RequisicaoConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long consultaId;
    private String cpfPaciente;
    private String tipoExame;
    private LocalDateTime horario;

    public RequisicaoConsulta(Long id, LocalDateTime horario, String tipoExame, String cpfPaciente, Long consultaId) {
        this.id = id;
        this.horario = horario;
        this.tipoExame = tipoExame;
        this.cpfPaciente = cpfPaciente;
        this.consultaId = consultaId;
    }

    public RequisicaoConsulta() {
    }

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

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }
}
