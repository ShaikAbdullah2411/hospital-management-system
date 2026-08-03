package com.hospitalmanagement.DoctorService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAvailabililtyRequest {

    private LocalDate availableDate;

    private LocalTime availableFrom;

    private LocalTime availableTo;

    private Integer slotDuration;
}
