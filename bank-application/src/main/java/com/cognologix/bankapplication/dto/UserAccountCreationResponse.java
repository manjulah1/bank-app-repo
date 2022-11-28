package com.cognologix.bankapplication.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserAccountCreationResponse {
    private Integer customer_id;
    private String successMessage;
    private String additionalInformation;
}
