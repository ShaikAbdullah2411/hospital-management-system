package com.hospitalmanagement.AppointmentService.feign;

import com.hospitalmanagement.AppointmentService.config.FeignInterceptor;
import com.hospitalmanagement.AppointmentService.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PATIENTSERVICE", configuration = FeignInterceptor.class)
public interface PatientFeignClient {

    @GetMapping("/patient/patientuserId/{userId}")
    PatientResponse getpatientbyUserId(@PathVariable Long userId);
}
