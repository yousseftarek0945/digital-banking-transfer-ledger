package com.qnbeyondplus.digital_banking_transfer_ledger.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {

    @NotBlank(message = "Account number is required")
    @Size(max = 20, message = "Account number must not exceed 20 characters")
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    @Size(max = 20, message = "Account type must not exceed 20 characters")
    private String accountType;

    private BigDecimal balance;

    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;

    @Pattern(
            regexp = "ACTIVE|INACTIVE|BLOCKED",
            message = "Status must be ACTIVE, INACTIVE, or BLOCKED"
    )
    private String status;
}