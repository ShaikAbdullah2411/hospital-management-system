package com.hospitalmanagement.AppointmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableSlotResponse {

    private LocalTime slotStartTime;

    private LocalTime slotEndTime;

    private boolean available;
}
