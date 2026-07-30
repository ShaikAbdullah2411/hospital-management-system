package com.hospitalmanagement.PatientService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class DoctorResponse {

    private Long id;
    private String doctorname;
    private String specialization;
    private String email;
    private String phone;
    private LocalTime availableFrom;
    private LocalTime availableTo;

}
