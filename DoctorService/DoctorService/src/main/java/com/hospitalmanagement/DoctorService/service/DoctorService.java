package com.hospitalmanagement.DoctorService.service;

import com.hospitalmanagement.DoctorService.dto.DoctorRequest;
import com.hospitalmanagement.DoctorService.entity.Doctor;
import com.hospitalmanagement.DoctorService.entity.Specialization;
import com.hospitalmanagement.DoctorService.exception.DoctorNotFoundException;
import com.hospitalmanagement.DoctorService.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public Doctor addDoctor(DoctorRequest request){

        Doctor doctor = Doctor.builder().doctorname(request.getDoctorname())
                .email(request.getEmail())
                .specialization(request.getSpecialization())
                .phone(request.getPhone())
                .availableFrom(request.getAvailableFrom())
                .availableTo(request.getAvailableTo())
                .active(true).build();

        return doctorRepository.save(doctor);
    }
    public List<Doctor> getAllDoctors()
    {
       return doctorRepository.findByActiveTrue();
    }
    public Doctor getDoctorById(Long id){
        return doctorRepository.findById(id).orElseThrow(() ->new DoctorNotFoundException("Doctor not found"));
    }

    public Doctor updateAvailability(Long id, LocalTime from, LocalTime to){

        Doctor doctor = getDoctorById(id);

        doctor.setAvailableFrom(from);

        doctor.setAvailableTo(to);

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id){

            Doctor doctor = getDoctorById(id);
            doctor.setActive(false);
            doctorRepository.save(doctor);
    }

    public Doctor getDoctorByName(String doctorname){

//        return doctorRepository.findAll().stream()
//                .filter(doctor -> doctor.getDoctorname() != null && doctor.getDoctorname().equalsIgnoreCase(doctorname))
//                .findFirst().orElseThrow(()->new DoctorNotFoundException("Doctor not available"));
        return doctorRepository.findByDoctornameIgnoreCaseAndActiveTrue(doctorname)
                .orElseThrow(()->new DoctorNotFoundException("Doctor not found"));
    }

    public List<Doctor> getDoctorBySpecialization(Specialization specialization){
//        return doctorRepository.findAll().stream()
//                .filter(doctor -> doctor.getSpecialization() == specialization).toList();
        return doctorRepository.findBySpecializationAndActiveTrue(specialization);
    }
}
