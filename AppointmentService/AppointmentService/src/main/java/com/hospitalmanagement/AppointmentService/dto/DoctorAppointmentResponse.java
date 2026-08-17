package com.hospitalmanagement.AppointmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAppointmentResponse {

    private Long id;

    private Long userId;

    private String doctorname;

    private String specialization;

    private String email;

    private String phone;

    private boolean active;
}
