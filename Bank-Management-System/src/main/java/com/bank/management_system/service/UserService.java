package com.bank.management_system.service;

import com.bank.management_system.model.User;
import com.bank.management_system.model.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService{
    User createUser(User user);
    User updateUser(Long userId, User user);
    void deleteUser(Long userId);
    User getUserById(Long userId);
    User getUserByUsername(String username);
    List<User> getUsersByRole(UserRole role);
    List<User> getAllActiveUsers();
    boolean usernameExists(String username);
    User changeUserStatus(Long userId, boolean active);
    UserDetails loadUserByUsername(String username);
}