package com.gajanan.Task_SpringBoot.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<String> handleBadCredentialException(BadCredentialsException badCredentialsException){
        return new ResponseEntity<String>(badCredentialsException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return new ResponseEntity<String>(entityNotFoundException.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
        return new ResponseEntity<String>(usernameNotFoundException.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
