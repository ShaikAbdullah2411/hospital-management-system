package com.hospitalmanagement.PatientService.feign;

import com.hospitalmanagement.PatientService.dto.CreatePatientRequest;
import com.hospitalmanagement.PatientService.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTHSERVICE")
public interface AuthFeignClient {

    @PostMapping("/auth/create-patient")
    PatientResponse createpatient(@RequestBody CreatePatientRequest request);
}
