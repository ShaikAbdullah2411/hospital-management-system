package com.hospitalmanagement.AppointmentService.controller;

import com.hospitalmanagement.AppointmentService.dto.AvailableSlotResponse;
import com.hospitalmanagement.AppointmentService.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {


    private final AppointmentService service;

    @GetMapping("/test")
    public String dotest(){
        return "appointments service working";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/availableslots")
    public ResponseEntity<List<AvailableSlotResponse>> getavailableslots(@RequestParam Long doctorId, @RequestParam LocalDate date){

        return new ResponseEntity<>(service.generateAvailableSlots(doctorId, date), HttpStatus.OK);
    }
}


