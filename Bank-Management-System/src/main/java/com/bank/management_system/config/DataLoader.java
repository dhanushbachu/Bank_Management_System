package com.bank.management_system.config;

import com.bank.management_system.model.*;
import com.bank.management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        // Only run if no users exist
        if (userRepository.count() == 0) {
            createInitialData();
        }
    }

    private void createInitialData() {
        System.out.println("=== Creating Initial Database Data ===");

        // 1. Create Manager
        User manager = new User();
        manager.setUsername("manager");
        manager.setPassword(passwordEncoder.encode("manager123"));
        manager.setRole(UserRole.MANAGER);
        manager.setEmployeeId("MGR001");
        manager.setEmployeeName("Branch Manager");
        manager.setBranch("Main Branch");
        manager.setCreatedAt(LocalDateTime.now());
        manager.setActive(true);
        userRepository.save(manager);
        System.out.println("Created Manager: manager / manager123");

        // 2. Create Cashier
        User cashier = new User();
        cashier.setUsername("cashier");
        cashier.setPassword(passwordEncoder.encode("cashier123"));
        cashier.setRole(UserRole.CASHIER);
        cashier.setEmployeeName("John Cashier");
        cashier.setEmployeeId("CSH001");
        cashier.setBranch("Main Branch");
        cashier.setCreatedAt(LocalDateTime.now());
        cashier.setActive(true);
        userRepository.save(cashier);
        System.out.println("Created Cashier: cashier / cashier123");

        // 3. Create Sample Customer with Account
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        customer.setEmail("alice@email.com");
        customer.setPhoneNo("1234567890");
        customer.setAddress("123 Main St, City");
        customer.setStatus(CustomerStatus.ACTIVE);
        customerRepository.save(customer);

        // Create User for Customer
        User customerUser = new User();
        customerUser.setUsername("alice");
        customerUser.setPassword(passwordEncoder.encode("alice123"));
        customerUser.setRole(UserRole.CUSTOMER);
        customerUser.setCustomer(customer);
        customerUser.setCreatedAt(LocalDateTime.now());
        customerUser.setActive(true);
        customerUser.setBranch("Main Branch");
        userRepository.save(customerUser);
        System.out.println("Created Customer: alice / alice123");

        // Create Account for Customer
        Account account = new Account();
        account.setAccountNo("ACC100001");
        account.setBalance(5000.0);
        account.setStatus(AccountStatus.ACTIVE);
        account.setAccountType(AccountType.SAVINGS);
        account.setCustomer(customer);
        account.setCreated_date(LocalDateTime.now());
        accountRepository.save(account);
        System.out.println("Created Account: ACC100001 with balance 5000");

        // 4. Create another Customer
        Customer customer2 = new Customer();
        customer2.setName("Bob Smith");
        customer2.setEmail("bob@email.com");
        customer2.setPhoneNo("0987654321");
        customer2.setAddress("456 Oak St, Town");
        customer2.setStatus(CustomerStatus.ACTIVE);
        customerRepository.save(customer2);

        User customerUser2 = new User();
        customerUser2.setUsername("bob");
        customerUser2.setPassword(passwordEncoder.encode("bob123"));
        customerUser2.setRole(UserRole.CUSTOMER);
        customerUser2.setCustomer(customer2);
        customerUser2.setCreatedAt(LocalDateTime.now());
        customerUser2.setActive(true);
        customerUser2.setBranch("Main Branch");
        userRepository.save(customerUser2);
        System.out.println("Created Customer: bob / bob123");

        Account account2 = new Account();
        account2.setAccountNo("ACC100002");
        account2.setBalance(3000.0);
        account2.setStatus(AccountStatus.ACTIVE);
        account2.setAccountType(AccountType.CURRENT);
        account2.setCustomer(customer2);
        account2.setCreated_date(LocalDateTime.now());
        accountRepository.save(account2);
        System.out.println("Created Account: ACC100002 with balance 3000");

        System.out.println("=== Initial Data Creation Complete ===");
    }

}