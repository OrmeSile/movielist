package dev.orme.movie.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class JsonExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handle(ResponseStatusException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ExceptionResponse(e.getStatusCode().value(), e.getReason()));
    }

    public record ExceptionResponse ( int code, String reason) {}
}
