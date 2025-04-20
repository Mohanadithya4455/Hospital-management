package com.hospital.doctor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleDoctorNotFound(DoctorNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Doctor Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorNameNotFound.class)
    public ResponseEntity<ErrorDetails> handleDoctornNameNotFound(DoctorNameNotFound ex)
    {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Doctor Name Not Found",
                ex.getMessage()

        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ErrorDetails> handleNotAllowed(NotAllowedException ex) {

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                "Not allowed",
                ex.getMessage()

        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
//    @ExceptionHandler(InvalidDateException.class)
//    public ResponseEntity<ErrorDetails> handleGenericException(InvalidDateException ex) {
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                HttpStatus.NOT_ACCEPTABLE.value(),
//                "Enter date correctly",
//                ex.getMessage()
//        );
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_ACCEPTABLE);
//    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }







}
