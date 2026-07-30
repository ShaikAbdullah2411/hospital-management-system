package com.hospitalmanagement.PatientService.feign;

import com.hospitalmanagement.PatientService.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "DOCTORSERVICE")
public interface DoctorFeignClient {

    @GetMapping("/doctor/alldoctors")
    List<DoctorResponse> listAllDoctors();

    @GetMapping("/doctor/{id}")
    DoctorResponse getDoctorbyId(@PathVariable Long id);

    @GetMapping("/doctor/search")
    List<DoctorResponse> searchDoctors(@RequestParam(required = false) String doctorname,
    @RequestParam(required = false) String specialization);



}
