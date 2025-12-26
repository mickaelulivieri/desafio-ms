package com.clinica_service.domain.enums;

public enum PrioridadeSintoma {
    BAIXA(1),
    PADRAO(2),
    ALTA(3),
    EMERGENCIAL(4);

    private final int nivel;

    PrioridadeSintoma(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}
