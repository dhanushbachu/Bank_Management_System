package com.bank.management_system.service.impl;

import com.bank.management_system.model.Customer;
import com.bank.management_system.model.CustomerStatus;
import com.bank.management_system.repository.CustomerRepository;
import com.bank.management_system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer customer) {
        Customer existingCustomer = getCustomerById(customerId);
        existingCustomer.setName(customer.getName());
        existingCustomer.setPhoneNo(customer.getPhoneNo());
        existingCustomer.setAddress(customer.getAddress());
        return customerRepository.save(existingCustomer);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> getCustomersByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }

    @Override
    public Customer changeCustomerStatus(Long customerId, CustomerStatus status) {
        Customer customer = getCustomerById(customerId);
        customer.setStatus(status);
        return customerRepository.save(customer);
    }

    @Override
    public boolean customerExistsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean customerExistsByPhone(String phone) {
        return customerRepository.existsByPhoneNo(phone);
    }
}