package com.cognologix.bankapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
public class BankStatementResponse {
    private String transactionId;

    private String transactionDate;

    private Double deposited;

    private Double withdrawn;

    private Double balance;

    public BankStatementResponse(String transactionId, String transactionDate, Double balanceAfterDeposition, Double balanceAfterWithdrawal, Double transactionAmount) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.deposited = balanceAfterDeposition;
        this.withdrawn = balanceAfterWithdrawal;
        this.balance = transactionAmount;
    }
}
