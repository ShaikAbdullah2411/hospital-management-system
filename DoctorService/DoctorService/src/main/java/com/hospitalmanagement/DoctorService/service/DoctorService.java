package com.hospitalmanagement.DoctorService.service;

import com.hospitalmanagement.DoctorService.dto.*;
import com.hospitalmanagement.DoctorService.entity.Doctor;
import com.hospitalmanagement.DoctorService.entity.DoctorAvailability;
import com.hospitalmanagement.DoctorService.entity.Specialization;
import com.hospitalmanagement.DoctorService.exception.DoctorNotFoundException;
import com.hospitalmanagement.DoctorService.feign.AuthFeignClient;
import com.hospitalmanagement.DoctorService.repository.DoctorAvailabilityRepository;
import com.hospitalmanagement.DoctorService.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final AuthFeignClient authFeignClient;

    private final DoctorAvailabilityRepository repository;


    public Doctor addDoctor(DoctorRequest request){

        CreateDoctorRequest authRequest = new CreateDoctorRequest();

        authRequest.setUsername(request.getDoctorname());
        authRequest.setEmail(request.getEmail());
        authRequest.setPassword(request.getPassword()); // You'll add this field

        DoctorResponse user = authFeignClient.createDoctor(authRequest);


        Doctor doctor = Doctor.builder()
                .userId(user.getId())
                .doctorname(request.getDoctorname())
                .email(request.getEmail())
                .specialization(request.getSpecialization())
                .phone(request.getPhone())
//                .availableFrom(request.getAvailableFrom())
//                .availableTo(request.getAvailableTo())
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

//    public Doctor updateAvailability(Long id, LocalDate date, LocalTime from, LocalTime to){
//
//        Doctor doctor = getDoctorById(id);
//
//        doctor.setAvailableDate(date);
//
//        doctor.setAvailableFrom(from);
//
//        doctor.setAvailableTo(to);
//
//        return doctorRepository.save(doctor);
//    }
    public DoctorAvailabilityResponse setAvailability(
            Long doctorId,
            DoctorAvailabililtyRequest request) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        DoctorAvailability availability =
                repository
                        .findByDoctorAndAvailableDate(
                                doctor,
                                request.getAvailableDate()
                        )
                        .orElse(DoctorAvailability.builder()
                                .doctor(doctor)
                                .build());

        availability.setAvailableDate(request.getAvailableDate());
        availability.setAvailableFrom(request.getAvailableFrom());
        availability.setAvailableTo(request.getAvailableTo());
        availability.setSlotDuration(
                request.getSlotDuration() == null
                        ? 20
                        : request.getSlotDuration()
        );
        availability.setActive(true);

        DoctorAvailability available = repository.save(availability);

        return DoctorAvailabilityResponse.builder()
                .id(available.getId())
                .doctorId(available.getDoctor().getId())
                .availableDate(available.getAvailableDate())
                .availableFrom(available.getAvailableFrom())
                .availableTo(available.getAvailableTo())
                .slotDuration(available.getSlotDuration())
                .active(available.isActive())
                .build();
    }

    public List<DoctorAvailabilityResponse> getDoctorAvailabilities(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        List<DoctorAvailability> availabilities =
                repository.findByDoctorAndActiveTrueOrderByAvailableDateAsc(doctor);

        return availabilities.stream()
                .map(availability -> DoctorAvailabilityResponse.builder()
                        .id(availability.getId())
                        .doctorId(doctor.getId())
                        .doctorname(doctor.getDoctorname())
                        .availableDate(availability.getAvailableDate())
                        .availableFrom(availability.getAvailableFrom())
                        .availableTo(availability.getAvailableTo())
                        .slotDuration(availability.getSlotDuration())
                        .active(availability.isActive())
                        .build())
                .toList();
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
