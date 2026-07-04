package com.hospitalmanagement.DoctorService.repository;

import com.hospitalmanagement.DoctorService.entity.Doctor;
import com.hospitalmanagement.DoctorService.entity.Specialization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void findByDoctornameIgnoreCaseAndActiveTrue() {

        Doctor doctor = Doctor.builder().doctorname("Abdullah")
                .email("abdullah@gmail.com")
                .specialization(Specialization.NEUROLOGIST)
                .active(true).build();

        doctorRepository.save(doctor);

        Optional<Doctor> result = doctorRepository.findByDoctornameIgnoreCaseAndActiveTrue("abdullah");

        assertTrue(result.isPresent());

        assertEquals("Abdullah", result.get().getDoctorname());



    }
}