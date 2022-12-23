package com.cognologix.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawAmountResponse extends BaseResponse {

    private Long accountNumber;

    private String withdrawalDate;

    private Double accountBalance;

    public WithdrawAmountResponse(String message, Boolean isSuccess, Long accountNumber, String withdrawalDate, Double accountBalance) {
        super(message, isSuccess);
        this.accountNumber = accountNumber;
        this.withdrawalDate = withdrawalDate;
        this.accountBalance = accountBalance;
    }
}
