package com.hospitalmanagement.AppointmentService.serviceImpl;

import com.hospitalmanagement.AppointmentService.dto.AvailableSlotResponse;
import com.hospitalmanagement.AppointmentService.dto.DoctorAvailabilityResponse;
import com.hospitalmanagement.AppointmentService.entity.Appointment;
import com.hospitalmanagement.AppointmentService.entity.AppointmentStatus;
import com.hospitalmanagement.AppointmentService.feign.DoctorFeignClient;
import com.hospitalmanagement.AppointmentService.repository.AppointmentRepository;
import com.hospitalmanagement.AppointmentService.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final DoctorFeignClient doctorFeignClient;

    private  final AppointmentRepository appointmentRepository;

    @Override
    public List<AvailableSlotResponse> generateAvailableSlots(Long doctorId, LocalDate appointmentDate) {

        DoctorAvailabilityResponse availability = doctorFeignClient.getAvailabilityByDate(doctorId, appointmentDate);
       List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentDateAndStatus(
               doctorId, appointmentDate, AppointmentStatus.CONFIRMED
       );
        Set<LocalTime> bookedSlotTimes = appointments.stream().map(Appointment::getSlotStartTime)
                .collect(Collectors.toSet());
        List<AvailableSlotResponse> slots = new ArrayList<>();
        LocalTime currenttime = availability.getAvailableFrom();
        LocalTime availableTo = availability.getAvailableTo();
        Integer slotDuration = availability.getSlotDuration();
        while (currenttime.plusMinutes(slotDuration).compareTo(availableTo)<=0){

            LocalTime slotEndTime = currenttime.plusMinutes(slotDuration);
            boolean available = !bookedSlotTimes.contains(currenttime);
            slots.add(AvailableSlotResponse.builder()
                    .slotStartTime(currenttime)
                    .slotEndTime(slotEndTime)
                    .available(available)
                    .build());
            currenttime = slotEndTime;
        }

        return slots;
    }
}
