package io.lucascarvalho_oliveira.FinancialManagement.exception;

import io.lucascarvalho_oliveira.FinancialManagement.exception.exceptions.*;
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

    @ExceptionHandler(PessoaExistente.class)
    public ResponseEntity<ErroResponse> handlePessoaExistente(
            PessoaExistente ex, HttpServletRequest request){

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return  new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> handleContaNaoEncontrada(
            ContaNaoEncontradaException ex, HttpServletRequest request){

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CodigoIncorretoExeption.class)
    public ResponseEntity<ErroResponse> handleCodigoIncorreto(
            CodigoIncorretoExeption ex, HttpServletRequest request){

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailEnvioExeption.class)
    public ResponseEntity<ErroResponse> handleEmailEnvio(
            EmailEnvioExeption ex, HttpServletRequest request){

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginExeption.class)
    public ResponseEntity<ErroResponse> handleLogin(
            LoginExeption ex, HttpServletRequest request){

        ErroResponse erro = new ErroResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }
}
