package ru.netology.file_manager.exception;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) //400
                .body("Bad credentials");
    }

    @ExceptionHandler(UploadFileException.class)
    public ResponseEntity<String> uploadFileException(UploadFileException uploadFileException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body("Error input data");
    }






}