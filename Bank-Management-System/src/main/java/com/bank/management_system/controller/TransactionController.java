package com.bank.management_system.controller;


import com.bank.management_system.model.Transaction;
import com.bank.management_system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction){
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId){
        Transaction transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId){
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/No/{accountNo}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountNumbers(@PathVariable String accountNo){
        List<Transaction> transactions = transactionService.getTransactionsByAccountNumbers(accountNo);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(@RequestParam LocalDateTime start,@RequestParam LocalDateTime end){
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(start,end);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Long userId){
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestParam String accountNo, @RequestParam BigDecimal amount, @RequestParam (required = false) String description){
        Transaction transaction = transactionService.deposit(accountNo,amount,description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestParam String accountNo, @RequestParam BigDecimal amount, @RequestParam(required = false) String description){
        Transaction transaction = transactionService.withdraw(accountNo, amount, description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam String fromAccount, @RequestParam String toAccount, @RequestParam BigDecimal amount, @RequestParam(required = false) String description) {
        Transaction transaction = transactionService.transfer(fromAccount, toAccount, amount, description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/cheque/deposit")
    public ResponseEntity<Transaction> chequeDeposit(@RequestParam String accountNo, @RequestParam BigDecimal amount, @RequestParam String chequeNo){
        Transaction transaction = transactionService.chequeDeposit(accountNo, amount, chequeNo);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/cheque/withdrawal")
    public ResponseEntity<Transaction> chequeWithdrawal(@RequestParam String accountNo, @RequestParam BigDecimal amount, @RequestParam String chequeNo){
        Transaction transaction = transactionService.chequeWithdrawal(accountNo,amount,chequeNo);
        return ResponseEntity.ok(transaction);
    }
}
