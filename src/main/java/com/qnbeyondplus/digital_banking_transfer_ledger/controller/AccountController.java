package com.qnbeyondplus.digital_banking_transfer_ledger.controller;

import com.qnbeyondplus.digital_banking_transfer_ledger.dto.AccountRequest;
import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Account;
import com.qnbeyondplus.digital_banking_transfer_ledger.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(
            @PathVariable Long customerId,
            @Valid @RequestBody AccountRequest request) {

        Account account = new Account();

        account.setAccountNumber(request.getAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());
        account.setCurrency(request.getCurrency());
        account.setStatus(request.getStatus());

        return accountService.createAccount(customerId, account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }
}