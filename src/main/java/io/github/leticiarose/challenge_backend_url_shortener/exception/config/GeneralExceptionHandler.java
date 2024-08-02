package io.github.leticiarose.challenge_backend_url_shortener.exception.config;

import io.github.leticiarose.challenge_backend_url_shortener.exception.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<String> handleUrlNotFoundException(UrlNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.valueOf(e.getErrorCode()));
    }

}