package com.cognologix.bankapplication.models;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @Column(nullable = false)
    @NotBlank(message = "Account holder name cannot be null.")
    private String accountHolderName;

    @Column(nullable = false)
    private String accountCreationDate;

    @Column(nullable = false)
    @NotBlank(message = "Account type cannot be null.")
    private String accountType;

    private String accountCurrency = "INR";

    @Column(nullable = false)
    @NotBlank(message = "Branch code cannot be null.")
    private String branchCode;

    @Column(nullable = false)
    private Double accountBalance;

    @Column(nullable = false)
    private Integer customerId;

    private String accountStatus = "ACTIVE";

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_user_id")
    private User user;

}
