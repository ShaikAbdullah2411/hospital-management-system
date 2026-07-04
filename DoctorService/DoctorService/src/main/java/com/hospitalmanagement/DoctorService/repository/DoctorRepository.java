package com.hospitalmanagement.DoctorService.repository;

import com.hospitalmanagement.DoctorService.entity.Doctor;
import com.hospitalmanagement.DoctorService.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByActiveTrue();

    Optional<Doctor> findByDoctornameIgnoreCaseAndActiveTrue(String doctorname);

    List<Doctor> findBySpecializationAndActiveTrue(Specialization specialization);
}
