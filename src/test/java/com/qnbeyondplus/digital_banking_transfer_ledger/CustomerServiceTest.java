package com.qnbeyondplus.digital_banking_transfer_ledger;

import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Customer;
import com.qnbeyondplus.digital_banking_transfer_ledger.exception.DuplicateCustomerException;
import com.qnbeyondplus.digital_banking_transfer_ledger.repository.CustomerRepository;
import com.qnbeyondplus.digital_banking_transfer_ledger.service.CustomerService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    CustomerService customerService;

    @Test
    void getCustomerById_shouldReturnCustomer() {

        Customer customer = new Customer();
        customer.setFullName("John Doe");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void createCustomer_withDuplicateEmail_shouldThrowException() {

        Customer customer = new Customer();
        customer.setEmail("john@example.com");
        customer.setPhone("01012345678");

        when(customerRepository.existsByEmail("john@example.com"))
                .thenReturn(true);

        when(customerRepository.existsByPhone("01012345678"))
                .thenReturn(false);

        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.createCustomer(customer, "John@123")
        );

        assertEquals(
                "Email already exists",
                exception.getErrors().get("email")
        );

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer_withDuplicatePhone_shouldThrowException() {

        Customer customer = new Customer();
        customer.setEmail("john@example.com");
        customer.setPhone("01012345678");

        when(customerRepository.existsByEmail("john@example.com"))
                .thenReturn(false);

        when(customerRepository.existsByPhone("01012345678"))
                .thenReturn(true);

        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.createCustomer(customer, "John@123")
        );

        assertEquals(
                "Phone already exists",
                exception.getErrors().get("phone")
        );

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer_withDuplicateEmailAndPhone_shouldReturnBothErrors() {

        Customer customer = new Customer();
        customer.setEmail("john@example.com");
        customer.setPhone("01012345678");

        when(customerRepository.existsByEmail("john@example.com"))
                .thenReturn(true);

        when(customerRepository.existsByPhone("01012345678"))
                .thenReturn(true);

        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.createCustomer(customer, "John@123")
        );

        assertEquals(
                "Email already exists",
                exception.getErrors().get("email")
        );

        assertEquals(
                "Phone already exists",
                exception.getErrors().get("phone")
        );

        assertEquals(2, exception.getErrors().size());

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void createCustomer_withValidData_shouldCreateCustomer() {

        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("01012345678");

        when(customerRepository.existsByEmail("john@example.com"))
                .thenReturn(false);

        when(customerRepository.existsByPhone("01012345678"))
                .thenReturn(false);

        when(passwordEncoder.encode("John@123"))
                .thenReturn("hashed-password");

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        Customer result =
                customerService.createCustomer(customer, "John@123");

        assertEquals("John Doe", result.getFullName());

        verify(passwordEncoder).encode("John@123");
        verify(customerRepository).save(customer);

        assertEquals(
                "hashed-password",
                customer.getPasswordHash()
        );
    }
}