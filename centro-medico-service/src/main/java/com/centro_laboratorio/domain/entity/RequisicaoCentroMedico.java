package com.centro_laboratorio.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "db_requisicao_centro_medico")
public class RequisicaoCentroMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long consultaId;

    private String cpfPaciente;
    private String tipoExame;
    private LocalDateTime dataSolicitacao;

    private String status;

    public RequisicaoCentroMedico(Long id, String status, LocalDateTime dataSolicitacao, String tipoExame, String cpfPaciente, Long consultaId) {
        this.id = id;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.tipoExame = tipoExame;
        this.cpfPaciente = cpfPaciente;
        this.consultaId = consultaId;
    }

    public RequisicaoCentroMedico() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusProcessamento() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }
}
