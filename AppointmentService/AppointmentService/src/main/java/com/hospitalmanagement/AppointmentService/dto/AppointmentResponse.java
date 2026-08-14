package com.hospitalmanagement.AppointmentService.dto;

import com.hospitalmanagement.AppointmentService.entity.AppointmentStatus;
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
public class AppointmentResponse {

    private Long appointmentId;

    private String appointmentToken;

    private Long patientUserId;

    private Long doctorId;

    private LocalDate appointmentDate;

    private LocalTime slotStartTime;

    private LocalTime slotEndTime;

    private AppointmentStatus status;

    private Integer queuePosition;

    private Integer estimatedWaitingMinutes;

    private String message;
}

