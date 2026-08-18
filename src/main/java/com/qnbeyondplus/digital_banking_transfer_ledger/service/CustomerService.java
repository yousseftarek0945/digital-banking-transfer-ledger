package com.qnbeyondplus.digital_banking_transfer_ledger.service;

import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Customer;
import com.qnbeyondplus.digital_banking_transfer_ledger.exception.DuplicateCustomerException;
import com.qnbeyondplus.digital_banking_transfer_ledger.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer createCustomer(Customer customer, String password) {

        Map<String, String> errors = new HashMap<>();

        if (customerRepository.existsByEmail(customer.getEmail())) {
            errors.put("email", "Email already exists");
        }

        if (customerRepository.existsByPhone(customer.getPhone())) {
            errors.put("phone", "Phone already exists");
        }

        if (!errors.isEmpty()) {
            throw new DuplicateCustomerException(errors);
        }

        LocalDateTime now = LocalDateTime.now();

        customer.setPasswordHash(passwordEncoder.encode(password));

        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found"));
    }
}