package org.example.migrator.exception.handler;

import lombok.RequiredArgsConstructor;
import org.example.migrator.controller.response.ErrorMessage;
import org.example.migrator.exception.FileAllowedException;
import org.example.migrator.exception.MigrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

import static org.example.migrator.enums.ApplicationErrorCodes.FATAL_ERROR;
import static org.example.migrator.enums.ApplicationErrorCodes.FILE_ALLOWED_ERROR;
import static org.example.migrator.enums.ApplicationErrorCodes.FILE_PARSING_ERROR;
import static org.example.migrator.enums.ApplicationErrorCodes.IO_ERROR;

@ControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOtherException(Exception e) {

        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .errorCode(FATAL_ERROR.getCodeId())
                        .errorMessage(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(FileAllowedException.class)
    public ResponseEntity<ErrorMessage> handleFileAllowedException(FileAllowedException e) {

        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .errorCode(FILE_ALLOWED_ERROR.getCodeId())
                        .errorMessage(e.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorMessage> handleIOException(IOException e) {

        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .errorCode(IO_ERROR.getCodeId())
                        .errorMessage(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MigrationException.class)
    public ResponseEntity<ErrorMessage> handleFileParsingException(MigrationException e) {

        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .errorCode(FILE_PARSING_ERROR.getCodeId())
                        .errorMessage(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
