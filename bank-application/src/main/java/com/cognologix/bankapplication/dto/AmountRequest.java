package com.cognologix.bankapplication.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AmountRequest {

    private Long accountNumber;

    private Double transactionAmount;

}
