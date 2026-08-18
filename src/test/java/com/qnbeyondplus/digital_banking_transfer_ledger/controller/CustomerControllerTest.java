package com.qnbeyondplus.digital_banking_transfer_ledger.controller;

import com.qnbeyondplus.digital_banking_transfer_ledger.dto.CustomerRequest;
import com.qnbeyondplus.digital_banking_transfer_ledger.dto.CustomerResponse;
import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Customer;
import com.qnbeyondplus.digital_banking_transfer_ledger.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void getAllCustomers_shouldReturnCustomers() {

        // Arrange
        Customer customer = new Customer();
        customer.setFullName("John Doe");

        when(customerService.getAllCustomers())
                .thenReturn(List.of(customer));

        // Act
        List<CustomerResponse> result =
                customerController.getAllCustomers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(
                "John Doe",
                result.get(0).getFullName()
        );
    }

    @Test
    void getCustomerById_shouldReturnCustomer() {

        // Arrange
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("john@example.com");

        when(customerService.getCustomerById(1L))
                .thenReturn(customer);

        // Act
        CustomerResponse result =
                customerController.getCustomerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(
                "John Doe",
                result.getFullName()
        );
        assertEquals(
                "john@example.com",
                result.getEmail()
        );
    }

    @Test
    void createCustomer_shouldCreateCustomerSuccessfully() {

        // Arrange
        CustomerRequest request = new CustomerRequest();

        request.setFullName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("01012345678");
        request.setPassword("John@123");

        Customer savedCustomer = new Customer();

        savedCustomer.setFullName("John Doe");
        savedCustomer.setEmail("john@example.com");
        savedCustomer.setPhone("01012345678");

        when(customerService.createCustomer(
                any(Customer.class),
                any(String.class)
        )).thenReturn(savedCustomer);

        // Act
        CustomerResponse result =
                customerController.createCustomer(request);

        // Assert
        assertNotNull(result);
        assertEquals(
                "John Doe",
                result.getFullName()
        );
        assertEquals(
                "john@example.com",
                result.getEmail()
        );
        assertEquals(
                "01012345678",
                result.getPhone()
        );
    }
}