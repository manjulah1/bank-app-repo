package com.cognologix.bankapplication.constants;

import lombok.Getter;

@Getter
public enum ExceptionMessageConstants {

    NULL_ARGUMENT(600, "Argument cannot be null."),
    INSUFFCIENT_INITIAL_BALANCE(606, "Account balance is insufficient for transaction."),

    RESOURCE_NOT_FOUND(604, "Requested resource is not found."),

    ZERO_AMOUNT(601, "Amount cannot be 0."),

    DEPOSITION_FAILED(603, "Amount deposition failed."),

    WITHDRAW_FAILED(605, "Amount withdrawal failed."),

    ACCOUNT_DELETION( 602, "Account does not exists."),

    ACCOUNT_CREATION_FAILED(607, "Account creation failed.");


    private final Integer errorCode;
    private final String error;

    ExceptionMessageConstants(Integer errorCode, String error) {
        this.errorCode = errorCode;
        this.error = error;
    }
}
