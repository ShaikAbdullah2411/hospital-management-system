package com.hospitalmanagement.PatientService.service;

import com.hospitalmanagement.PatientService.dto.CreatePatientRequest;
import com.hospitalmanagement.PatientService.dto.DoctorResponse;
import com.hospitalmanagement.PatientService.dto.PatientRequest;
import com.hospitalmanagement.PatientService.dto.PatientResponse;
import com.hospitalmanagement.PatientService.entity.Patient;
import com.hospitalmanagement.PatientService.exception.PatientNotFoundException;
import com.hospitalmanagement.PatientService.feign.AuthFeignClient;
import com.hospitalmanagement.PatientService.feign.DoctorFeignClient;
import com.hospitalmanagement.PatientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    @Autowired
    private final PatientRepository patientRepository;

    private  final DoctorFeignClient doctorFeignClient;

    private final AuthFeignClient authFeignClient;

    public Patient registerPatient(PatientRequest request){


        System.out.println("Received request: " + request);
        patientRepository.findByEmail(request.getEmail()).ifPresent(patient1 -> {
            throw new PatientNotFoundException("Email already exists");
        });

        CreatePatientRequest authRequest = CreatePatientRequest.builder()
                .patientname(request.getPatientname())
                .email(request.getEmail())
//                .password(request.getPassword())
                .build();

        PatientResponse user = authFeignClient.createpatient(authRequest);

        Patient patient = Patient.builder()
                .userId(user.getId())
                .patientname(request.getPatientname())
                .email(request.getEmail()).age(request.getAge())
                .phone(request.getPhone())
                .active(true).build();



        return patientRepository.save(patient);

    }

    public PatientResponse createPatient(CreatePatientRequest request){

        Patient patient = Patient.builder().userId(request.getUserId())
                .patientname(request.getPatientname())
                .email(request.getEmail())
                .active(true)
                .build();
        Patient savepatient = patientRepository.save(patient);

        return PatientResponse.builder().id(savepatient.getId())
                .patientname(savepatient.getPatientname())
                .email(savepatient.getEmail()).build();

    }

    public Patient updatePatient(Long userId, PatientRequest request){

        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new PatientNotFoundException("Patient profile not found"));

        patientRepository.findByPatientname(request.getPatientname()).ifPresent(
                patient1 -> {

                        if (!patient1.getId().equals(patient.getId())) {
                            throw new PatientNotFoundException("Email already exists");
                        }
                });

        patient.setPatientname(request.getPatientname());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setAge(request.getAge());


        return patientRepository.save(patient);

    }

    public List<Patient> getAllPatients(){

        return patientRepository.findByActiveTrue();
    }

    public Patient getPatientById(Long id){

        return patientRepository.findById(id).orElseThrow(()-> new PatientNotFoundException("patient not found"));
    }

    public void deletePatient(Long id){

        Patient patient = getPatientById(id);
        patient.setActive(false);
        patientRepository.save(patient);
    }
    public Patient getPatientByName(String patientname){

        return patientRepository.findByPatientname(patientname)
                .orElseThrow(()-> new PatientNotFoundException("patient name not found"));
    }

//    public Patient updatePatient(Long id, PatientRequest request){
//
//            Patient patient = getPatientById(id);
//
//            patient.setPatientname(request.getPatientname());
//            patient.setEmail(request.getEmail());
//            patient.setPhone(request.getPhone());
//            patient.setAge(request.getAge());
//
//            return patientRepository.save(patient);
//
//    }

    public List<DoctorResponse> getAllDoctors(){

            return doctorFeignClient.listAllDoctors();
    }

    public DoctorResponse getDoctorById(Long id){

        return doctorFeignClient.getDoctorbyId(id);
    }

    public List<DoctorResponse> searchDoctor(String doctorname, String specialization){

        return doctorFeignClient.searchDoctors(doctorname, specialization);
    }

}
