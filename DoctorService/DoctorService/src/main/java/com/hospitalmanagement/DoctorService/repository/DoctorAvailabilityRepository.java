package com.hospitalmanagement.DoctorService.repository;

import com.hospitalmanagement.DoctorService.entity.Doctor;
import com.hospitalmanagement.DoctorService.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorAvailabilityRepository  extends JpaRepository<DoctorAvailability, Long> {

    Optional<DoctorAvailability> findByDoctorAndAvailableDate(Doctor doctor, LocalDate availableDate);

    Optional<DoctorAvailability> findByDoctorAndAvailableDateAndActiveTrue(Doctor doctor, LocalDate availableDate);

    List<DoctorAvailability> findByDoctor(Doctor doctor);

    List<DoctorAvailability> findByDoctorAndActiveTrue(Doctor doctor);

    List<DoctorAvailability> findByAvailableDate(LocalDate date);

    List<DoctorAvailability> findByDoctorAndActiveTrueOrderByAvailableDateAsc(Doctor doctor);

}
