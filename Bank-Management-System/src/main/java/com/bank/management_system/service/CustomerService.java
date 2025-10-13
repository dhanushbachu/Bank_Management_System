package com.bank.management_system.service;

import com.bank.management_system.model.Customer;
import com.bank.management_system.model.CustomerStatus;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long customerId, Customer customer);
    Customer getCustomerById(Long customerId);
    Customer getCustomerByEmail(String email);
    List<Customer> getAllCustomers();
    List<Customer> getCustomersByStatus(CustomerStatus status);
    Customer changeCustomerStatus(Long customerId, CustomerStatus status);
    boolean customerExistsByEmail(String email);
    boolean customerExistsByPhone(String phone);
}