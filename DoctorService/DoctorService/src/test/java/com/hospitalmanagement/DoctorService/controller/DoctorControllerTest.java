package com.hospitalmanagement.DoctorService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalmanagement.DoctorService.dto.DoctorRequest;
import com.hospitalmanagement.DoctorService.entity.Doctor;
import com.hospitalmanagement.DoctorService.entity.Specialization;
import com.hospitalmanagement.DoctorService.service.DoctorService;
import com.hospitalmanagement.DoctorService.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(DoctorController.class)
@AutoConfigureMockMvc(addFilters = false)  // skips security filters entirely for this test
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DoctorService doctorService;

    @MockitoBean
    private JwtUtil jwtUtil;   // <-- add this



    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addDoctor() throws Exception {

        DoctorRequest request = new DoctorRequest();

        request.setDoctorname("Abdullah");

        request.setEmail("abdullah@gmail.com");

        request.setPhone("7731018970");

        request.setSpecialization(Specialization.NEUROLOGIST);

        request.setAvailableFrom(LocalTime.of(9,0));
        request.setAvailableTo(LocalTime.of(17, 0));

        Doctor savedDoctor = Doctor.builder()
                .id(1L)
                .doctorname("Abdullah")
                .email("abdullah@gmail.com")
                .phone("7731018970")
                .specialization(Specialization.NEUROLOGIST)
                .availableFrom(LocalTime.of(9, 0))
                .availableTo(LocalTime.of(17, 0))
                .active(true)
                .build();

        Mockito.when(doctorService.addDoctor(Mockito.any())).thenReturn(savedDoctor);

        mockMvc.perform(post("/doctor/addDoc").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.doctorname").value("Abdullah"))
                .andExpect(jsonPath("$.email").value("abdullah@gmail.com"));
    }
}