package com.newsletter.controller;


import com.newsletter.exception.AlreadyAnswered;
import com.newsletter.exception.UpdateFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyAnswered.class)
    private ResponseEntity<String> userAnsweredOnce(AlreadyAnswered exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(UpdateFailedException.class)
    private ResponseEntity<String> errorWhileQuery(UpdateFailedException exception){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }
}
