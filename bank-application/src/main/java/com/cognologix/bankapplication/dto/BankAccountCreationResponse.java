package com.cognologix.bankapplication.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BankAccountCreationResponse extends BaseResponse {
    private Long generatedAccountNumber;

    public BankAccountCreationResponse(Long generatedAccountNumber, String message, Boolean isSuccess) {
        super(message, isSuccess);
        this.generatedAccountNumber = generatedAccountNumber;
    }
}
