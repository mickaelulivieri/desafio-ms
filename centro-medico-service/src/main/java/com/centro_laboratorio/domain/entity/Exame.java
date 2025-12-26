package com.centro_laboratorio.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_exames")
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cpfPaciente;

    @Column(nullable = false)
    private String nomeExame;

    private LocalDateTime horario;

    private String prioridade;

    private boolean altaComplexidade;

    public Exame(Long id, boolean altaComplexidade, String prioridade, LocalDateTime horario, String nomeExame, String cpfPaciente) {
        this.id = id;
        this.altaComplexidade = altaComplexidade;
        this.prioridade = prioridade;
        this.horario = horario;
        this.nomeExame = nomeExame;
        this.cpfPaciente = cpfPaciente;
    }

    public Exame() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAltaComplexidade() {
        return altaComplexidade;
    }

    public void setAltaComplexidade(boolean altaComplexidade) {
        this.altaComplexidade = altaComplexidade;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }
}
