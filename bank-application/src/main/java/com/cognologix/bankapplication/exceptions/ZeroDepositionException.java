package com.cognologix.bankapplication.exceptions;

public class ZeroDepositionException extends RuntimeException {

    public ZeroDepositionException(String message) {
        super(message);
    }
}
