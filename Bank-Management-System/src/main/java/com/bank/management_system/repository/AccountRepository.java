package com.bank.management_system.repository;

import com.bank.management_system.model.Account;
import com.bank.management_system.model.AccountStatus;
import com.bank.management_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNo(String accountNo);
    List<Account> findByCustomer(Customer customer);
    List<Account> findByStatus(AccountStatus status);
    List<Account> findByCustomer_CustomerId(Long customerId);
    boolean existsByAccountNo(String accountNo);
}
