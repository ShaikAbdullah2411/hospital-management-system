package com.hospitalmanagement.authService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {

    private Long id;

    private String patientname;

    private String email;
}
