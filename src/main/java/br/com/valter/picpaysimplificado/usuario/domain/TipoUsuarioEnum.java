package br.com.valter.picpaysimplificado.usuario.domain;

import lombok.Getter;

@Getter
public enum TipoUsuarioEnum {

    COMUM("Comum"),
    LOJISTA("Lojista");

    private final String descricao;

    public boolean isComum(){
        return COMUM.equals(this);
    }

    public boolean isLojista(){
        return LOJISTA.equals(this);
    }

    TipoUsuarioEnum(String descricao){
        this.descricao = descricao;
    }
}
