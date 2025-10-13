package com.bank.management_system.controller;


import com.bank.management_system.model.Customer;
import com.bank.management_system.model.CustomerStatus;
import com.bank.management_system.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId,@RequestBody Customer customer){
        Customer updatedCustomer = customerService.updateCustomer(customerId,customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId){
        Customer customer = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email){
        Customer customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }


    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Customer>> getCustomersByStatus(@RequestBody CustomerStatus customerStatus){
        List<Customer> customers = customerService.getCustomersByStatus(customerStatus);
        return ResponseEntity.ok(customers);
    }

    @PatchMapping("{customerId}/status")
    public ResponseEntity<Customer> changeCustomerStatus(@PathVariable Long customerId,@RequestBody CustomerStatus customerStatus){
        Customer customer = customerService.changeCustomerStatus(customerId,customerStatus);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> customersExistsByEmail(@PathVariable String email){
        Boolean exists = customerService.customerExistsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/phone/{phone}")
    public ResponseEntity<Boolean> customersExistsByPhone(@PathVariable String phone){
        Boolean exists = customerService.customerExistsByPhone(phone);
        return ResponseEntity.ok(exists);
    }
}
