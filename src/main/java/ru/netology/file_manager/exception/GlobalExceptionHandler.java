package ru.netology.file_manager.exception;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsException(BadCredentialsException badCredentialsException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) //400
                .body("Bad credentials");
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> httpClientErrorException(HttpClientErrorException httpClientErrorException) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) //401
                .body("Unauthorized error");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body("Error input data");
    }

/*    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleAllExceptions(IllegalArgumentException ex, WebRequest request) {
        String logMessage = String.format("Exception: %s, message: %s, request: %s",
                ex.getClass().getName(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerException(NullPointerException nullPointerException) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) //500
                .body("Error upload file");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> ioException(IOException ioException) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error upload file");
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<String> incorrectResultSizeDataAccessException(IncorrectResultSizeDataAccessException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Query did not return a unique result: 2 results were returned");
    }


}