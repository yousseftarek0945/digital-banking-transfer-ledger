package com.qnbeyondplus.digital_banking_transfer_ledger.exception;

import java.util.Map;

public class DuplicateCustomerException extends RuntimeException {

    private final Map<String, String> errors;

    public DuplicateCustomerException(Map<String, String> errors) {
        super("Duplicate customer data");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}