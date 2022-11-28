package com.cognologix.bankapplication.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private String transactionId;

    private Double transactionAmount;

    private LocalDate transactionDate;

    private String fromAccountNumber;

    private String fromAccountHolderName;

    private String fromAccountType;

    private String fromIfscCode;

    private String toAccountNumber;

    private String toAccountHolderName;

    private String branchName;

    private String branchCity;

    private String branchState;

    private String branchCountry;

    private String branchPincode;

}
