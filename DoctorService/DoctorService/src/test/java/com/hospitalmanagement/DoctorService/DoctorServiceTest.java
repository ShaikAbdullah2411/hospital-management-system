package com.hospitalmanagement.DoctorService;

import com.hospitalmanagement.DoctorService.dto.DoctorRequest;
import com.hospitalmanagement.DoctorService.entity.Doctor;
import com.hospitalmanagement.DoctorService.entity.Specialization;
import com.hospitalmanagement.DoctorService.exception.DoctorNotFoundException;
import com.hospitalmanagement.DoctorService.repository.DoctorRepository;
import com.hospitalmanagement.DoctorService.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void addDoctor() {

        DoctorRequest request = new DoctorRequest();

        request.setDoctorname("Abdullah");
        request.setEmail("abdullah@gmail.com");
        request.setPhone("7731018970");
        request.setSpecialization(Specialization.NEUROLOGIST);
        request.setAvailableFrom(LocalTime.of(9,0));
        request.setAvailableTo(LocalTime.of(17, 0));

        Doctor saveDoctor = Doctor.builder().id(1L)
                .doctorname("Abdullah").email("abdullah@gmail.com").phone("7731018970")
                .specialization(Specialization.NEUROLOGIST).active(true).build();
       org.mockito.Mockito.when(doctorRepository.save(ArgumentMatchers.any(Doctor.class))).thenReturn(saveDoctor);

        Doctor result = doctorService.addDoctor(request);
        assertNotNull(result);
        assertEquals("Abdullah", result.getDoctorname());
        Mockito.verify(doctorRepository, Mockito.times(1)).save(ArgumentMatchers.any(Doctor.class));
    }

    @Test
    void getAllDoctors() {
    }

    @Test
    void getDoctorById() {

        Doctor doctor = Doctor.builder().id(1L)
                .doctorname("Abdullah").build();
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Doctor doctorById = doctorService.getDoctorById(1L);
        assertEquals("Abdullah", doctorById.getDoctorname());

    }

    @Test
    void testDoctorNotFound(){
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DoctorNotFoundException.class, ()-> doctorService.getDoctorById(1L));
    }
}