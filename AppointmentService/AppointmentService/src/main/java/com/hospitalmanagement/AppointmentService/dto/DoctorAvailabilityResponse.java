package com.hospitalmanagement.AppointmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAvailabilityResponse {

    private Long id;

    private Long doctorId;

    private LocalDate availableDate;

    private LocalTime availableFrom;

    private LocalTime availableTo;

    private Integer slotDuration;

    private boolean active;

}
