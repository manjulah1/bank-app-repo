package com.cognologix.bankapplication.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bank_id;

    @Column(nullable = false,updatable = false)
    private Long accountNumber;

    private String accountHolderName;

    private LocalDate accountCreationDate;

    private String accountType;

    private String accountCurrency = "INR";

    private String branchName;

//    private String branchCity;
//
//    private String branchState;
//
//    private String branchCountry;
//
//    private String branchPincode;

    private String ifscCode;

    private Double accountBalance;

    private Integer customerId;

    private String accountStatus = "ACTIVE";

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "bank_user_id")
//    private User;

//    private String aadharCardDocPath;

//    private String panCardDocPath;
}
