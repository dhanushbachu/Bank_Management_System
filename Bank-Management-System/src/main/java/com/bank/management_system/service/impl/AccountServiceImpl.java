package com.bank.management_system.service.impl;

import com.bank.management_system.model.Account;
import com.bank.management_system.model.AccountStatus;
import com.bank.management_system.repository.AccountRepository;
import com.bank.management_system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long accountId, Account account) {
        Account existingAccount = getAccountById(accountId);
        existingAccount.setAccountType(account.getAccountType());
        existingAccount.setStatus(account.getStatus());
        return accountRepository.save(existingAccount);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));
    }

    @Override
    public Account getAccountByNo(String accountNo) {
        return accountRepository.findByAccountNo(accountNo)
                .orElseThrow(() -> new RuntimeException("Account not found with number: " + accountNo));
    }

    @Override
    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomer_CustomerId(customerId);
    }

    @Override
    public List<Account> getAccountsByStatus(AccountStatus status) {
        return accountRepository.findByStatus(status);
    }



    @Override
    public Account changeAccountStatus(Long accountId, AccountStatus status) {
        Account account = getAccountById(accountId);
        account.setStatus(status);
        return accountRepository.save(account);
    }

    @Override
    public boolean accountNoExists(String accountNo) {
        return accountRepository.existsByAccountNo(accountNo);
    }

    @Override
    public double getAccountBalance(Long accountId) {
        return getAccountById(accountId).getBalance();
    }

    @Override
    public double getAccountBalance(String accountNo) {
        return getAccountByNo(accountNo).getBalance();
    }
}