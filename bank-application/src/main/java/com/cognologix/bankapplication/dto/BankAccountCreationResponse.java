package com.cognologix.bankapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class BankAccountCreationResponse {
    private Long generatedAccountNumber;
    private Boolean success = false;
    private String message = "Bank account creation failed.";
}
