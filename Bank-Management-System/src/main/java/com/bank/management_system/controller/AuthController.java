package com.bank.management_system.controller;

import com.bank.management_system.model.User;
import com.bank.management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.getUserByUsername(loginRequest.getUsername());

            // In real application, use PasswordEncoder to check password
            // For now, simple check (you'll implement proper auth later)
            if (user.getPassword().equals(loginRequest.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("user", user);
                response.put("message", "Login successful");
                response.put("token", "jwt-token-placeholder"); // You'll implement JWT later
                return ResponseEntity.ok(response);
            } else {
                throw new RuntimeException("Invalid credentials");
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @GetMapping("/validate/{username}")
    public ResponseEntity<Boolean> validateUsername(@PathVariable String username) {
        boolean exists = userService.usernameExists(username);
        return ResponseEntity.ok(exists);
    }



    // Inner class for login request
    public static class LoginRequest {
        private String username;
        private String password;
        //private String role; // MANAGER, CASHIER, CUSTOMER

        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
       // public String getRole() { return role; }
       // public void setRole(String role) { this.role = role; }
    }
}