package com.curso.hefishing.api.exception;

import com.curso.hefishing.service.RecursoDuplicadoException;
import com.curso.hefishing.service.RecursoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiError> tratarNaoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request) {

        return criarResposta(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ApiError> tratarDuplicado(
            RecursoDuplicadoException ex,
            HttpServletRequest request) {

        return criarResposta(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI(),
                Map.of()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> tratarValidacao(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> fields = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fields.put(error.getField(), error.getDefaultMessage())
                );

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                "Dados inválidos",
                request.getRequestURI(),
                fields
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> tratarJsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return criarResposta(
                HttpStatus.BAD_REQUEST,
                "JSON inválido ou malformado",
                request.getRequestURI(),
                Map.of()
        );
    }

    private ResponseEntity<ApiError> criarResposta(
            HttpStatus status,
            String message,
            String path,
            Map<String, String> fields) {

        ApiError erro = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                fields
        );

        return ResponseEntity.status(status).body(erro);
    }
}
