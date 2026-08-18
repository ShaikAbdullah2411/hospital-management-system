package com.hospitalmanagement.AppointmentService.service;


import com.hospitalmanagement.AppointmentService.dto.AvailableSlotResponse;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    List<AvailableSlotResponse> generateAvailableSlots(Long doctorId, LocalDate appointmentDate);
}
