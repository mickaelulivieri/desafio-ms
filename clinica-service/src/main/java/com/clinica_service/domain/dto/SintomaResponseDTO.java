package com.clinica_service.domain.dto;

import com.clinica_service.domain.enums.PrioridadeSintoma;

public class SintomaResponseDTO {

    private Long id;
    private String nome;
    private String doencasRelacionadas;
    private PrioridadeSintoma prioridade;

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
