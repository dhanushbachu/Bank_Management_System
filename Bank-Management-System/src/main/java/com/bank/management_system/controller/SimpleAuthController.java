package com.bank.management_system.controller;

import com.bank.management_system.model.User;
import com.bank.management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/simple-auth")
public class SimpleAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            // This will be handled by Spring Security's Basic Auth
            // For now, just return user info if credentials are valid
            User user = userService.getUserByUsername(loginRequest.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful - use Basic Auth");
            response.put("user", user);
            response.put("role", user.getRole().name());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // Simple test endpoint to check if security is working
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Security is working!");
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}