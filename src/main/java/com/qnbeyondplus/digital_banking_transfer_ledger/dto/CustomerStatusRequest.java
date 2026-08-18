package com.qnbeyondplus.digital_banking_transfer_ledger.dto;

import com.qnbeyondplus.digital_banking_transfer_ledger.entity.CustomerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerStatusRequest {

    @NotNull(message = "Customer status is required")
    private CustomerStatus status;
}