package com.hospitalmanagement.PatientService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreatePatientRequest {

    private Long userId;

    private String patientname;

    private String email;

//    private String password;
}
