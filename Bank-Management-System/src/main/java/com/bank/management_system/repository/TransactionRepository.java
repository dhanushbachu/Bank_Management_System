package com.bank.management_system.repository;

import com.bank.management_system.model.Account;
import com.bank.management_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccount(Account account);
    List<Transaction> findByToAccount(Account account);
    List<Transaction> findByFromAccountOrToAccount(Account fromAccount, Account toAccount);
    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findByProcessedBy_UserId(Long userId);
}
