package com.afterlife.wow_afterlife_api.exception;

import com.afterlife.wow_afterlife_api.dto.RegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<RegisterResponse> accountAlreadyExists(
            AccountAlreadyExistsException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new RegisterResponse(
                        false,
                        exception.getMessage()
                ));
    }
}
