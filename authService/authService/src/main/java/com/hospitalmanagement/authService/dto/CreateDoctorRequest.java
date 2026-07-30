package com.hospitalmanagement.authService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDoctorRequest {

    private String username;

    private String email;

    private String password;
}
