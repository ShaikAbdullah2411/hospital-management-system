package com.hospitalmanagement.authService.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DoctorResponse {

    private Long id;

    private String username;

    private String email;
}
