package com.bank.management_system.service.impl;


import com.bank.management_system.model.Account;
import com.bank.management_system.model.Transaction;
import com.bank.management_system.model.TransactionType;
import com.bank.management_system.repository.AccountRepository;
import com.bank.management_system.repository.TransactionRepository;
import com.bank.management_system.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Transaction createTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long transactionId){
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return transactionRepository.findByFromAccountOrToAccount(account,account);
    }

    @Override
    public List<Transaction> getTransactionsByAccountNumbers(String accountNumber) {
        Account account = accountRepository.findByAccountNo(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return transactionRepository.findByFromAccountOrToAccount(account, account);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByTimestampBetween(start, end);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByProcessedBy_UserId(userId);
    }

    @Override
    public Transaction deposit(String accountNo, BigDecimal amount, String description){
        Account account = accountRepository.findByAccountNo(accountNo)
                .orElseThrow(()->new RuntimeException("Account not found"));

        account.setBalance(account.getBalance());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setDescription(description);
        transaction.setToAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction withdraw(String accountNo, BigDecimal amount, String description){
        Account account = accountRepository.findByAccountNo(accountNo)
                .orElseThrow(()->new RuntimeException("Account not found"));

        if(account.getBalance()<amount.doubleValue()){
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(account.getBalance()- amount.doubleValue());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setDescription(description);
        transaction.setFromAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction transfer(String fromAccount, String toAccount, BigDecimal amount, String description){
        Account fromAccountNo = accountRepository.findByAccountNo(fromAccount)
                .orElseThrow(()->new RuntimeException("From account not found"));
        Account toAccountNo = accountRepository.findByAccountNo(toAccount)
                .orElseThrow(()->new RuntimeException("To account not found"));

        if(fromAccountNo.getBalance()<amount.doubleValue()){
            throw new RuntimeException("Insufficient Balance");
        }

        fromAccountNo.setBalance(fromAccountNo.getBalance()- amount.doubleValue());
        toAccountNo.setBalance(toAccountNo.getBalance()+amount.doubleValue());
        accountRepository.save(fromAccountNo);
        accountRepository.save(toAccountNo);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setDescription(description);
        transaction.setFromAccount(fromAccountNo);
        transaction.setToAccount(toAccountNo);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction chequeDeposit(String accountNo, BigDecimal amount, String chequeNo) {
        Account account = accountRepository.findByAccountNo(accountNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount.doubleValue());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.CHEQUE_DEPOSIT);
        transaction.setChequeNo(chequeNo);
        transaction.setToAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction chequeWithdrawal(String accountNo, BigDecimal amount, String chequeNo){
        Account account = accountRepository.findByAccountNo(accountNo)
                .orElseThrow(()->new RuntimeException("Account not found"));

        if(account.getBalance()<amount.doubleValue()){
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance()+amount.doubleValue());
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.CHEQUE_WITHDRAWAL);
        transaction.setChequeNo(chequeNo);
        transaction.setFromAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}
