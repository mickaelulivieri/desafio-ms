package com.clinica_service.domain.dto;

public class SintomaRequestDTO {

    private String nome;
    private String doencasRelacionadas;
    private String prioridade; // 🔥 STRING, NÃO ENUM

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDoencasRelacionadas() {
        return doencasRelacionadas;
    }

    public void setDoencasRelacionadas(String doencasRelacionadas) {
        this.doencasRelacionadas = doencasRelacionadas;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }
}
