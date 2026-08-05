package io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions;

public class ContaNaoEncontradaException extends RuntimeException{

    public ContaNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
