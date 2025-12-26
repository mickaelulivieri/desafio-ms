package com.clinica_service.domain.entity;

import com.clinica_service.domain.enums.PrioridadeSintoma;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_sintoma")
public class Sintoma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String doencasRelacionadas;

    @Enumerated(EnumType.STRING)
    private PrioridadeSintoma prioridade;

    public Sintoma() {
    }

    public Sintoma(Long id, PrioridadeSintoma prioridade, String doencasRelacionadas, String nome) {
        this.id = id;
        this.prioridade = prioridade;
        this.doencasRelacionadas = doencasRelacionadas;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrioridadeSintoma getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(PrioridadeSintoma prioridade) {
        this.prioridade = prioridade;
    }

    public String getDoencasRelacionadas() {
        return doencasRelacionadas;
    }

    public void setDoencasRelacionadas(String doencasRelacionadas) {
        this.doencasRelacionadas = doencasRelacionadas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
