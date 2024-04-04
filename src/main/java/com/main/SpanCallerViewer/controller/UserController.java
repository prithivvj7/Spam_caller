package com.main.SpanCallerViewer.controller;

import com.main.SpanCallerViewer.model.UserDetails;
import com.main.SpanCallerViewer.model.UserLogin;
import com.main.SpanCallerViewer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity register(@Validated @RequestBody UserDetails userDetails){
        return service.regigter(userDetails);
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLogin userLogin){
        return service.login(userLogin);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader String id){
        return  service.logout(id);
    }
}
