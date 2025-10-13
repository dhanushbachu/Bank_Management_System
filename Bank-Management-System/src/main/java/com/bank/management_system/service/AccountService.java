package com.bank.management_system.service;

import com.bank.management_system.model.Account;
import com.bank.management_system.model.AccountStatus;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);
    Account updateAccount(Long accountId, Account account);
    Account getAccountById(Long accountId);
    Account getAccountByNo(String accountNo);
    List<Account> getAccountsByCustomerId(Long customerId);
    List<Account> getAccountsByStatus(AccountStatus status);
    Account changeAccountStatus(Long accountId, AccountStatus status);
    boolean accountNoExists(String accountNo);
    double getAccountBalance(Long accountId);
    double getAccountBalance(String accountNo);
}