package com.cognologix.bankapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositAmountResponse extends BaseResponse {

    private Long accountNumber;

    private Double depositedAmount;

    private String depositDate;

    public DepositAmountResponse(String message, Boolean isSuccess, Long accountNumber, Double depositedAmount, String depositDate) {
        super(message, isSuccess);
        this.accountNumber = accountNumber;
        this.depositedAmount = depositedAmount;
        this.depositDate = depositDate;
    }
}
