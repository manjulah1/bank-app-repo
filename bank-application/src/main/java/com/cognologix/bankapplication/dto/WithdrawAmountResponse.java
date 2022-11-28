package com.cognologix.bankapplication.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class WithdrawAmountResponse {

    private Long accountNumber;

    private LocalDate withdrawalDate;

    private Double accountBalance;

    private String message = "Amount withdrawal failed.";
}
