package com.hospitalmanagement.DoctorService.dto;

import com.hospitalmanagement.DoctorService.entity.Specialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAppointmentsResponse {

    private Long id;

    private Long userId;

    private String doctorname;

    private Specialization specialization;

    private String email;

    private String phone;

    private boolean active;

}
