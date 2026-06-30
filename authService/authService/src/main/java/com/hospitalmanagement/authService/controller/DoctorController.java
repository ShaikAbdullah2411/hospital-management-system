package com.hospitalmanagement.authService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/doctor")
public class DoctorController {

    @PostMapping("/testdoc")
    public ResponseEntity<String> seyHello() {
        return ResponseEntity.ok("Hello from doctor Controller!");
    }
}
