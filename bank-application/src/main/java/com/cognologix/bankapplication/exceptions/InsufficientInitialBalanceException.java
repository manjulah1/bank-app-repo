package com.cognologix.bankapplication.exceptions;

public class InsufficientInitialBalanceException extends RuntimeException{
    public InsufficientInitialBalanceException(String message) {
        super(message);
    }
}
