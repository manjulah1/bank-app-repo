package com.cognologix.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class TransferAmountResponse extends BaseResponse {
    private Long fromAccountNumber;

    private Long toAccountNumber;

    private Double transactionAmount;

    public TransferAmountResponse(String message, Boolean isSuccess, Long fromAccountNumber, Long toAccountNumber, Double transactionAmount) {
        super(message, isSuccess);
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.transactionAmount = transactionAmount;
    }
}
