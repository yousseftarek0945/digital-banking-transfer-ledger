package com.qnbeyondplus.digital_banking_transfer_ledger.controller;

import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Customer;
import com.qnbeyondplus.digital_banking_transfer_ledger.security.SecurityConfig;
import com.qnbeyondplus.digital_banking_transfer_ledger.service.CustomerService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@Import(SecurityConfig.class)
class CustomerControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    @Test
    void createCustomer_shouldReturn201() throws Exception {

        Customer customer = new Customer();

        customer.setFullName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("01012345678");

        when(customerService.createCustomer(
                any(Customer.class),
                any(String.class)
        )).thenReturn(customer);

        mockMvc.perform(
                post("/api/customers")
                        .with(csrf())
                        .contentType("application/json")
                        .content("""
                                {
                                    "fullName": "John Doe",
                                    "email": "john@example.com",
                                    "phone": "01012345678",
                                    "password": "John@123"
                                }
                                """)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.fullName").value("John Doe"))
        .andExpect(jsonPath("$.email").value("john@example.com"))
        .andExpect(jsonPath("$.phone").value("01012345678"));
    }

    @Test
    void createCustomer_withInvalidData_shouldReturn400() throws Exception {

        mockMvc.perform(
                post("/api/customers")
                        .with(csrf())
                        .contentType("application/json")
                        .content("""
                                {
                                    "fullName": "",
                                    "email": "invalid-email",
                                    "phone": "12345",
                                    "password": "John@123"
                                }
                                """)
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.fullName")
                .value("Full name is required"))
        .andExpect(jsonPath("$.errors.email")
                .value("Invalid email format"))
        .andExpect(jsonPath("$.errors.phone")
                .value("Phone must be 11 digits and start with 010, 011, 012, or 015"));
    }
}