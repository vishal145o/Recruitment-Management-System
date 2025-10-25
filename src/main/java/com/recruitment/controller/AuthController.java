package com.recruitment.controller;

import com.recruitment.entity.User;
import com.recruitment.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User req) {
        if (req.getEmail() == null || req.getPassword() == null || req.getName() == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "name, email and password required"));
        }
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "email already exists"));
        }
        // store password as simple MD5 hash for demo (do not use in production)
        String hashed = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());
        req.setPassword(hashed);
        userRepository.save(req);
        return ResponseEntity.ok(Map.of("success", true, "message", "User registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "email and password required"));
        }
        Optional<User> u = userRepository.findByEmail(email);
        if (u.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "invalid credentials"));
        }
        String hashed = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!u.get().getPassword().equals(hashed)) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "invalid credentials"));
        }
        return ResponseEntity.ok(Map.of("success", true, "message", "Login successful", "user", u.get()));
    }
}
