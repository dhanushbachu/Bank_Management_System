package com.bank.management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.management_system.model.User;
import com.bank.management_system.model.UserRole;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{
    Optional<User> findByUsername(String username);
    List<User> findByRole(UserRole role);
    List<User> findByRoleAndActiveTrue(UserRole role);
    boolean existsByUsername(String username);
    Optional<User> findByEmployeeId(String employeeId);
}
