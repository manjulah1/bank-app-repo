package com.cognologix.bankapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NullArgumentException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NullArgumentException() {
    }
    public NullArgumentException(String message) {
        super(message);
    }
}
