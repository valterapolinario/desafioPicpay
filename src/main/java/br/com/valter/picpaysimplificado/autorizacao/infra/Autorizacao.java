package br.com.valter.picpaysimplificado.autorizacao.infra;

public record Autorizacao(
        String message
) {

    public static final String AUTORIZADO = "Autorizado";

    public boolean isAutorizado(){
        return AUTORIZADO.equals(message);
    }
}
