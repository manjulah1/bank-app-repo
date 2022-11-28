package com.cognologix.bankapplication.models;


import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "customer_name", nullable = false)
    @NotBlank(message = "Customer name cannot be empty.")
    private String customerName;

    @Column(name = "customer_address", nullable = false)
    @NotBlank(message = "Customer address cannot be empty.")
    private String customerAddress;

    @Column(name = "customer_city", nullable = false)
    @NotBlank(message = "Customer city cannot be empty.")
    private String customerCity;

    @Column(name = "customer_state", nullable = false)
    @NotBlank(message = "Customer state cannot be empty.")
    private String customerState;

    @Column(name = "customer_country", nullable = false)
    @NotBlank(message = "Customer country cannot be empty.")
    private String customerCountry;

    @Column(name = "customer_pincode", nullable = false)
    @NotBlank(message = "Customer pincode cannot be empty.")
    private String customerPincode;

    @Column(name = "customer_email", nullable = false)
    @NotBlank(message = "Customer email cannot be empty.")
    private String customerEmail;

    @Column(name = "phone_number", nullable = false)
    @NotBlank(message = "Customer phone number cannot be empty.")
    @Size(min = 10, max = 10)
    private String phoneNumber;

    @Column(name = "aadhar_number", nullable = false)
    @NotBlank(message = "Customer AADHAR number cannot be empty.")
    @Size(min = 12, max = 12)
    private String aadharNumber;

    @Column(name = "pan_number", nullable = false)
    @NotBlank(message = "Customer PAN number cannot be empty.")
    @Size(min = 10, max = 10)
    private String panNumber;

    @NotBlank(message = "Account status cannot be empty.")
    private String userStatus = "ACTIVE";

}
