package com.hospitalmanagement.DoctorService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CreateDoctorRequest {

    private String username;

    private String email;

    private String password;
}
