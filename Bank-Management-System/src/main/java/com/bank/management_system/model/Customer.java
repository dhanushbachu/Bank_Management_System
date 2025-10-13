package com.bank.management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "customers")

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(name="phone_number",length=12)
    private String phoneNo;

    private String Address;


    @JsonIgnore  // Prevent infinite loops in JSON
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Account> accounts=new ArrayList<>();

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Feedback> Feedbacks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.PENDING_APPROVAL;


    public Customer() {
    }

    public Customer(String name, String email, String phoneNo, String address) {
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        Address = address;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Feedback> getFeedbacks() {
        return Feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        Feedbacks = feedbacks;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
}
