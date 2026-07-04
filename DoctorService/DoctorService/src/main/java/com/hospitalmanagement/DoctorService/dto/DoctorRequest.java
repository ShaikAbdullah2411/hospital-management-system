package com.hospitalmanagement.DoctorService.dto;

import com.hospitalmanagement.DoctorService.entity.Specialization;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DoctorRequest {

    @NotBlank(message = "Doctor name is required")
    private String doctorname;

    @NotNull(message = "specialization is required")
    private Specialization specialization;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    private LocalTime availableFrom;

    private LocalTime availableTo;
}
