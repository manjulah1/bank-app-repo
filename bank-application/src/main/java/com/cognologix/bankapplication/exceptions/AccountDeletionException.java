package com.cognologix.bankapplication.exceptions;

public class AccountDeletionException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public AccountDeletionException() {
    }

    public AccountDeletionException(String message) {
        super(message);
    }
}
