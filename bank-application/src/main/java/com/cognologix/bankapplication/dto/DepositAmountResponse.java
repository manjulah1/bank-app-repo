package com.cognologix.bankapplication.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class DepositAmountResponse {

    private Long accountNumber;

    private Double depositedAmount;

    private LocalDate depositDate;

    private String message = "Amount deposition failed.";
}
