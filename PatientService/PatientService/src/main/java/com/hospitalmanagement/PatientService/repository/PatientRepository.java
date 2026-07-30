package com.hospitalmanagement.PatientService.repository;

import com.hospitalmanagement.PatientService.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByActiveTrue();

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByPatientname(String patientname);

    Optional<Patient> findByUserId(Long userId);
}
