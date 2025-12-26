package com.clinica_service.domain.dto;

import java.util.List;

public class AtendimentoResponseDTO {

    private List<String> possiveisDoencas;

    private String mensagem;

    private String codigoExame;

    public List<String> getPossiveisDoencas() {
        return possiveisDoencas;
    }

    public void setPossiveisDoencas(List<String> possiveisDoencas) {
        this.possiveisDoencas = possiveisDoencas;
    }

    public String getCodigoExame() {
        return codigoExame;
    }

    public void setCodigoExame(String codigoExame) {
        this.codigoExame = codigoExame;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
