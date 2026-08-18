package com.qnbeyondplus.digital_banking_transfer_ledger.repository;

import com.qnbeyondplus.digital_banking_transfer_ledger.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}