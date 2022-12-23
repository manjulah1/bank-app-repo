package com.cognologix.bankapplication.constants;

public enum Constants {
    ACTIVE("ACTIVE"),

    INACTIVE("INACTIVE"),

    DEPOSIT("DEPOSIT"),

    WITHDRAW("WITHDRAW"),

    USER_DELETION_FAILED("User deletion failed."),

    USER_DELETION_SUCCESSFUL("User deleted successfully."),

    ACCOUNT_CREATED("Account created Successfully."),

    AMOUNT_DEPOSITED("Amount deposited successfully."),

    AMOUNT_WITHDRAWAL("Amount withdrawal was successful."),

    TRANSACTION_SUCCESSFUL("Transaction completed successfully."),

    SHOW_BALANCE_FAILED("Failed to fetch account balance - Account INACTIVE."),

    SHOW_BALANCE_SUCCESSFUL("Successfully fetched account balance."),

    BANK_ACCOUNT_DELETION_SUCCESSFUL("Bank account deleted successfully."),

    BANK_ACCOUNT_DELETION_FAILED("Bank account deletion failed.");
    private final String message;

    Constants(String message) {
        this.message = message;
    }
}
