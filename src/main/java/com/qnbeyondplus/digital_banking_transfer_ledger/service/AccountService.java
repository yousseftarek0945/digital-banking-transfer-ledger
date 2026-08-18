package com.qnbeyondplus.digital_banking_transfer_ledger.service;

import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Account;
import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Customer;
import com.qnbeyondplus.digital_banking_transfer_ledger.repository.AccountRepository;
import com.qnbeyondplus.digital_banking_transfer_ledger.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public Account createAccount(Long customerId, Account account) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found"));

        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }

        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }

        if (account.getCurrency() == null || account.getCurrency().isBlank()) {
            account.setCurrency("EGP");
        }

        if (account.getStatus() == null || account.getStatus().isBlank()) {
            account.setStatus("ACTIVE");
        }

        LocalDateTime now = LocalDateTime.now();

        account.setCustomer(customer);
        account.setCreatedAt(now);
        account.setUpdatedAt(now);

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));
    }
}