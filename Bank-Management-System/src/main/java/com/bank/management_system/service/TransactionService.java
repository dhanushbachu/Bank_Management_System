package com.bank.management_system.service;

import com.bank.management_system.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(Long TransactionId);
    List<Transaction> getTransactionsByAccountId(Long accountId);
    List<Transaction> getTransactionsByAccountNumbers(String accountNo);
    List<Transaction> getTransactionsByDateRange(LocalDateTime start, LocalDateTime end);
    List<Transaction> getTransactionsByUserId(Long userId);

    Transaction deposit(String accountNo, BigDecimal amount, String description);
    Transaction withdraw(String accountNo, BigDecimal amount, String description);
    Transaction transfer(String fromAccount, String toAccount, BigDecimal amount, String description);
    Transaction chequeDeposit(String accountNo, BigDecimal amount, String chequeNo);
    Transaction chequeWithdrawal(String accountNo, BigDecimal amount, String chequeNo);
}
