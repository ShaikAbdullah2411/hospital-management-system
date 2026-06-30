package com.hospitalmanagement.authService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/admin")
public class AdminController {

    @PostMapping("/testadmin")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello from admin controller");
    }
}
