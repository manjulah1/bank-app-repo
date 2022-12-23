package com.cognologix.bankapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InsufficientInitialBalanceException extends RuntimeException{
    public InsufficientInitialBalanceException(String message) {
        super(message);
    }
}
