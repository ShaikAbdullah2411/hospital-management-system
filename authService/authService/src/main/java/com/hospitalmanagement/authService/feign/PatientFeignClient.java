package com.hospitalmanagement.authService.feign;

import com.hospitalmanagement.authService.dto.CreatePatientRequest;
import com.hospitalmanagement.authService.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PATIENTSERVICE")
public interface PatientFeignClient {

    @PostMapping("/patient/private/create-patient")
    PatientResponse createPatient(@RequestBody CreatePatientRequest request);


}
