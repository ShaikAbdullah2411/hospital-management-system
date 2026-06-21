package com.hospitalmanagement.AppointmentService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @GetMapping("/test")
    public String dotest(){
        return "appointments service working";
    }
}
