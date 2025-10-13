package com.bank.management_system.controller;

import com.bank.management_system.model.Account;
import com.bank.management_system.model.AccountStatus;
import com.bank.management_system.repository.AccountRepository;
import com.bank.management_system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;


    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId,@RequestBody Account account){
        Account updatedAccount = accountService.updateAccount(accountId,account);
        return ResponseEntity.ok(updatedAccount);
    }



    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId){
        Account account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/number/{accountNo}")
    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNo){
        Account account = accountService.getAccountByNo(accountNo);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable Long customerId){
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Account>>  getAccountsByStatus(@RequestBody AccountStatus accountStatus){
        List<Account> accounts = accountService.getAccountsByStatus(accountStatus);
        return ResponseEntity.ok(accounts);
    }

    @PatchMapping("/{accountId}/status")
    public ResponseEntity<Account> changeAccountStatus(@PathVariable Long accountId,@RequestBody AccountStatus accountStatus){
        Account account = accountService.changeAccountStatus(accountId,accountStatus);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/exists/{accountNo}")
    public ResponseEntity<Boolean> accountNumberExists(@PathVariable String accountNo){
        Boolean exists = accountService.accountNoExists(accountNo);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable Long accountId){
        Double balance = accountService.getAccountBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/number/{accountNo}/balance")
    public ResponseEntity<Double>  getAccountBalance(@PathVariable String accountNo){
        Double balance = accountService.getAccountBalance(accountNo);
        return ResponseEntity.ok(balance);
    }
}