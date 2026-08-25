package com.hospitalmanagement.AppointmentService.controller;

import com.hospitalmanagement.AppointmentService.dto.AppointmentRequest;
import com.hospitalmanagement.AppointmentService.dto.AppointmentResponse;
import com.hospitalmanagement.AppointmentService.dto.AvailableSlotResponse;
import com.hospitalmanagement.AppointmentService.dto.PatientResponse;
import com.hospitalmanagement.AppointmentService.feign.PatientFeignClient;
import com.hospitalmanagement.AppointmentService.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {


    private final AppointmentService service;

    private final PatientFeignClient feignClient;

    @GetMapping("/test")
    public String dotest(){
        return "appointments service working";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/availableslots")
    public ResponseEntity<List<AvailableSlotResponse>> getavailableslots(@RequestParam Long doctorId, @RequestParam LocalDate date){

        return new ResponseEntity<>(service.generateAvailableSlots(doctorId, date), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/test-patient/{userId}")
    public ResponseEntity<PatientResponse> testpatient(@PathVariable Long userId){

        return new ResponseEntity<>(feignClient.getpatientbyUserId(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @PostMapping("/book")
    public ResponseEntity<AppointmentResponse> bookAppointment(@Valid @RequestBody AppointmentRequest request){

        return new ResponseEntity<>(service.bookAppointment(request), HttpStatus.CREATED);
    }
}


