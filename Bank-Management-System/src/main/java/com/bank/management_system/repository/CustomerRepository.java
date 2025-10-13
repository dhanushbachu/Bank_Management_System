package com.bank.management_system.repository;

import com.bank.management_system.model.Customer;
import com.bank.management_system.model.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    List<Customer> findByStatus(CustomerStatus status);
    boolean existsByEmail(String email);
    boolean existsByPhoneNo(String phoneNo);
}
