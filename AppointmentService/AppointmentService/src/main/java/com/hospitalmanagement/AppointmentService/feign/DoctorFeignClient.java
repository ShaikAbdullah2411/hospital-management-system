package com.hospitalmanagement.AppointmentService.feign;

import com.hospitalmanagement.AppointmentService.config.FeignInterceptor;
import com.hospitalmanagement.AppointmentService.dto.DoctorAppointmentResponse;
import com.hospitalmanagement.AppointmentService.dto.DoctorAvailabilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "DOCTORSERVICE", configuration = FeignInterceptor.class)
public interface DoctorFeignClient {

    @GetMapping("/doctor/{id}")
    DoctorAppointmentResponse getDoctorbyId_2(@PathVariable Long id);

    @GetMapping("/doctor/{doctorId}/checkavailability")
    DoctorAvailabilityResponse getAvailabilityByDate(@PathVariable Long doctorId, @RequestParam LocalDate date);
}
