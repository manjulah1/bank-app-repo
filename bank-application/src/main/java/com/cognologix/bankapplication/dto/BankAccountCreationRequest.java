package com.cognologix.bankapplication.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Data
public class BankAccountCreationRequest {
    private Integer customerId;

    @NotBlank(message = "Account type cannot be empty.")
    private String accountType;

    private Double initialDepositAmount;
}
