package com.hospitalmanagement.DoctorService.feign;

import com.hospitalmanagement.DoctorService.dto.CreateDoctorRequest;
import com.hospitalmanagement.DoctorService.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTHSERVICE")
public interface AuthFeignClient {

    @PostMapping("/auth/create-doctor")
    DoctorResponse createDoctor(@RequestBody CreateDoctorRequest request);
}
