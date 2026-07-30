package com.hospitalmanagement.PatientService.controller;

import com.hospitalmanagement.PatientService.dto.CreatePatientRequest;
import com.hospitalmanagement.PatientService.dto.DoctorResponse;
import com.hospitalmanagement.PatientService.dto.PatientRequest;
import com.hospitalmanagement.PatientService.dto.PatientResponse;
import com.hospitalmanagement.PatientService.entity.Patient;
import com.hospitalmanagement.PatientService.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    @Autowired
    private final PatientService patientService;

    @GetMapping("/test")
    public String doTest(){
        return "Patient service is running";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addPatient")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody PatientRequest request){

        Patient patient = patientService.registerPatient(request);

        return new ResponseEntity<>(patient, HttpStatus.CREATED);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @PutMapping("/profile/{userId}")
    public ResponseEntity<Patient> updatePatient(@Valid @PathVariable Long userId,
                                                 @RequestBody PatientRequest request){

        Patient patient = patientService.updatePatient(userId, request);

        return new ResponseEntity<>(patient, HttpStatus.CREATED);

    }
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/allpatients")
    public ResponseEntity<List<Patient>> getAllPatients(){

        List<Patient> allPatients = patientService.getAllPatients();

        return new ResponseEntity<>(allPatients, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getpatientById(@PathVariable Long id){

        Patient patientById = patientService.getPatientById(id);

        return new ResponseEntity<>(patientById, HttpStatus.OK);

    }
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/search")
    public ResponseEntity<Patient> getPatientByName(@RequestParam String patientname){

        Patient patientByName = patientService.getPatientByName(patientname);

        return new ResponseEntity<>(patientByName, HttpStatus.OK);

    }
//    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
//    @PutMapping("/{id}")
//    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequest request){
//
//        Patient patient = patientService.updatePatient(id, request);
//        return new ResponseEntity<>(patient, HttpStatus.OK);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id){

        patientService.deletePatient(id);

        return new ResponseEntity<>("patient deleted successfully", HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponse>> getAllDoctors(){


        return new ResponseEntity<>(patientService.getAllDoctors(), HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id){

        return new ResponseEntity<>(patientService.getDoctorById(id), HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/doctors/search")
    public ResponseEntity<List<DoctorResponse>> searchDoctors(@RequestParam(required = false) String doctorname,
                                                              @RequestParam(required = false) String specialization){

        return new ResponseEntity<>(patientService.searchDoctor(doctorname, specialization), HttpStatus.OK);
    }

    @PostMapping("/private/create-patient")
    public ResponseEntity<PatientResponse> createPatient(@RequestBody CreatePatientRequest request){

        return new ResponseEntity<>(patientService.createPatient(request), HttpStatus.CREATED);
    }

}
