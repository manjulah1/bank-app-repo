package com.cognologix.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountCreationRequest {

    @NotBlank
    private String accountType;

    @NotBlank
    private Double initialDepositAmount;

    private String branchName;

    private Integer customerId;
}
