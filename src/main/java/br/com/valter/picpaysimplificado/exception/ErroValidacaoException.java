package br.com.valter.picpaysimplificado.exception;

public class ErroValidacaoException extends RuntimeException{
    public ErroValidacaoException(String message) {
        super(message);
    }
}
