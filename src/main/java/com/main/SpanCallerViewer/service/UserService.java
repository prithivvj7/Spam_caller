package com.main.SpanCallerViewer.service;


import com.main.SpanCallerViewer.model.UserDetails;
import com.main.SpanCallerViewer.model.UserLogin;
import com.main.SpanCallerViewer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLogsService logsService;

    @Autowired
    private ContactsService contactsService;


    public ResponseEntity regigter(UserDetails newUser) {
        UserDetails existingUser = userRepository.findByNumber(newUser.getNumber());
        if (existingUser == null) {
            UserDetails savedUser = userRepository.save(newUser);
            contactsService.userAddContact(savedUser.getUsername(), savedUser.getNumber(), savedUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Number alredy exist.");
        }
    }

    public ResponseEntity login(UserLogin userLogin) {
        UserDetails existingUser = userRepository.findByNumber(userLogin.getNumber());
        if (existingUser.getNumber().equals(userLogin.getNumber())) {
            if (existingUser.getPassword().equals(userLogin.getPassword())) {
                HashMap response = new HashMap<>();
                String key = generateKey();
                logsService.userLoginIn(existingUser.getId(), key);
                response.put("key", key);
                response.put("message", "User loggedin successfully.");
                response.put("userid", existingUser.getId());
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User password is invalid.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist.");
    }

    private static String generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    public ResponseEntity logout(String id) {
        logsService.userLogout(id);
        return ResponseEntity.status(HttpStatus.OK).body("User logged out successfully.");
    }

}
