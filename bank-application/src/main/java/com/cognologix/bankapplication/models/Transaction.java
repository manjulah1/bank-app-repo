package com.cognologix.bankapplication.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String transactionId;

    private Double transactionAmount;

    private String transactionDate;

    private Long fromAccountNumber;

    private Long toAccountNumber;

    private Double balanceAfterWithdrawal;

    private Double balanceAfterDeposition;

    public Transaction(String transactionId, Double transactionAmount, String transactionDate, Long toAccountNumber, Double balanceAfterDeposition) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.toAccountNumber = toAccountNumber;
        this.balanceAfterDeposition = balanceAfterDeposition;
    }

    public Transaction(String transactionId, Double transactionAmount, String now, Long fromAccountNumber, Long toAccountNumber, Double balanceAfterWithdrawal, Double balanceAfterDeposition) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.transactionDate = now;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.balanceAfterWithdrawal = balanceAfterWithdrawal;
        this.balanceAfterDeposition = balanceAfterDeposition;
    }
}
