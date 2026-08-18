package com.qnbeyondplus.digital_banking_transfer_ledger.controller;

import com.qnbeyondplus.digital_banking_transfer_ledger.dto.CustomerRequest;
import com.qnbeyondplus.digital_banking_transfer_ledger.dto.CustomerResponse;
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
    public CustomerResponse createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        Customer customer = new Customer();

        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        Customer createdCustomer =
                customerService.createCustomer(
                        customer,
                        request.getPassword()
                );

        return toResponse(createdCustomer);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {

        return customerService.getAllCustomers()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(
            @PathVariable Long id) {

        Customer customer =
                customerService.getCustomerById(id);

        return toResponse(customer);
    }

    private CustomerResponse toResponse(Customer customer) {

        return new CustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus()
        );
    }
}