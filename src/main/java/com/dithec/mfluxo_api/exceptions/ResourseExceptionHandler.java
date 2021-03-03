package com.dithec.mfluxo_api.exceptions;

import com.dithec.mfluxo_api.exceptions.customExceptionMessages.DataBaseException;
import com.dithec.mfluxo_api.exceptions.customExceptionMessages.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice // Anotação que intercepta as excessões que acontecerem
public class ResourseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;
    private FieldError fieldError;

    // GET de entidades por um id inexistente no BD
    @ExceptionHandler(ResourceNotFoundException.class) // Intercepta excessões do tipo ResourceNotFoundException
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        String error = "Resource Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    // DELETE Entidades que possuem associação com outras
    @ExceptionHandler(DataBaseException.class) // Intercepta excessões do tipo ResourceNotFoundException
    public ResponseEntity<StandardError> dataBase(DataBaseException ex, HttpServletRequest request) {
        String error = "Erro no Banco de Dados - Associções Existentes";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(standardError);
    }

    // POST de entidades que não podem ser lidas (Atributos inseridos no Json que não existem na Entidade)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //String mensagemUsuario = messageSource.getMessage("mensagen.invalida", null, LocaleContextHolder.getLocale());
        String mensagemUsuario = "O Json do obj requerido possue campos desconhecidos!";
        String mensagemDesenvolvedor = ex.getCause().toString();
        List<StandardError> standardError = Arrays.asList(new StandardError(Instant.now(), status.value(), mensagemUsuario, mensagemDesenvolvedor, request.toString()));
        return ResponseEntity.status(status).body(standardError);

    }

    // PUT com atributos da entidade invális(null onde é NOT NULL etc...)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String mensagemUsuario=null;
        String mensagemDesenvolvedor=null;
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            mensagemUsuario = messageSource.getMessage(fieldError, null);
            mensagemDesenvolvedor = fieldError.toString();

        }
        List<StandardError> standardError = Arrays.asList(new StandardError(Instant.now(), status.value(), mensagemUsuario, mensagemDesenvolvedor, request.toString()));
        return handleExceptionInternal(ex, standardError, headers, status.BAD_REQUEST, request);
    }


}


