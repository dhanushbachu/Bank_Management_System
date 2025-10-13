package com.bank.management_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="accounts")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false,unique = true)
    private String accountNo;

    @Column()
    private double balance = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status=AccountStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private LocalDateTime created_date=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="customer_id",nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "fromAccount",cascade = CascadeType.ALL)
    private List<Transaction> sentTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount",cascade = CascadeType.ALL)
    private List<Transaction> receivedTransactions = new ArrayList<>();

    public Account() {
    }

    public Account(String accountNo, AccountType accountType, Customer customer) {
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transaction> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transaction> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }
}
