package ru.netology.file_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UploadFileException.class)
    public ResponseEntity<String> uploadFileException(UploadFileException uploadFileException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body("Error input data");
    }

    @ExceptionHandler(AuthenticationUserException.class)
    public ResponseEntity<String> authenticatonUserException(AuthenticationUserException authenticatonUserException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body("Bad credentials");
    }

    @ExceptionHandler(DeleteFileException.class)
    public ResponseEntity<String> deleteFileException(DeleteFileException deleteFileException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 400
                .body("Error input data");
    }

    @ExceptionHandler(ErrorDeleteFileException.class)
    public ResponseEntity<String> errorDeleteFileException(ErrorDeleteFileException errorDeleteFileException) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                .body("Error delete file");
    }


}