package io.lucascarvalho_oliveira.FinancialManagement.exception;

import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.ContaNaoEncontradaException;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.PessoaNaoEncontradaException;
import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.SenhaInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<ErroResponse> handleSenhaInvalida(
        SenhaInvalidaException ex, HttpServletRequest request){

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> handlePessoaNaoEncontrada(
            PessoaNaoEncontradaException ex, HttpServletRequest request){

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ErroResponse> handleContaNaoEncontrada(
            ContaNaoEncontradaException ex, HttpServletRequest request){

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }
}
