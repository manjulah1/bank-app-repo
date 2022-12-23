package com.cognologix.bankapplication.exceptions;

import com.cognologix.bankapplication.constants.ExceptionMessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullArgumentException extends RuntimeException {
    Integer statusCode;
    public NullArgumentException() {
    }
    public NullArgumentException(String message) {
        super(message);
        this.statusCode = ExceptionMessageConstants.NULL_ARGUMENT.getErrorCode();
    }
}
