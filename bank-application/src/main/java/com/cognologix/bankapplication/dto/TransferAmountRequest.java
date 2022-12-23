package com.cognologix.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class TransferAmountRequest {

    private Long fromAccountNumber;

    private Long toAccountNumber;

    private Double transactionAmount;

    public TransferAmountRequest(Long fromAccountNumber, Long toAccountNumber, Double transactionAmount) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.transactionAmount = transactionAmount;
    }
}
