package com.sinalez.sinaleasy_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sinalez.sinaleasy_back.exceptions.customExceptions.CityIdNotFoundException;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.CityNotFoundException;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.SignalNotFoundException;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.UserNotFoundException;



@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(SignalNotFoundException.class)
    private ResponseEntity<RestErrorMessage> signalNotFoundException(SignalNotFoundException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(CityNotFoundException.class)
    private ResponseEntity<RestErrorMessage> cityNotFoundException(CityNotFoundException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(CityIdNotFoundException.class)
    private ResponseEntity<RestErrorMessage> cityIdNotFoundException(CityIdNotFoundException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundException(UserNotFoundException exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
    
}