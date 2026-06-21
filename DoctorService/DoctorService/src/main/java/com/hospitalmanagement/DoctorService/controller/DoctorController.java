package com.hospitalmanagement.DoctorService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @GetMapping("/test")
    public String dotest(){
        return "Doctor service is working";
    }
}
