package br.com.valter.picpaysimplificado.usuario.domain;

import lombok.Getter;

@Getter
public enum TipoUsuarioEnum {

    COMUM("Comum"),
    LOJISTA("Lojista");

    private final String descricao;

    TipoUsuarioEnum(String descricao){
        this.descricao = descricao;
    }
}
