package com.cognologix.bankapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ZeroAmountException extends RuntimeException {

    public ZeroAmountException(String message) {
        super(message);
    }
}
