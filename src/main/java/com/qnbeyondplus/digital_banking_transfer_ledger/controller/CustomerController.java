package com.qnbeyondplus.digital_banking_transfer_ledger.controller;

import com.qnbeyondplus.digital_banking_transfer_ledger.dto.CustomerRequest;
import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Customer;
import com.qnbeyondplus.digital_banking_transfer_ledger.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        Customer customer = new Customer();

        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        return customerService.createCustomer(
                customer,
                request.getPassword()
        );
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}