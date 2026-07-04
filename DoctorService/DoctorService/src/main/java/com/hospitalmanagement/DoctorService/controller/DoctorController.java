package com.hospitalmanagement.DoctorService.controller;

import com.hospitalmanagement.DoctorService.dto.DoctorRequest;
import com.hospitalmanagement.DoctorService.entity.Doctor;
import com.hospitalmanagement.DoctorService.entity.Specialization;
import com.hospitalmanagement.DoctorService.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    @Autowired
    private final DoctorService doctorService;

    @GetMapping("/test")
    public String dotest(){
        return "Doctor service is working";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addDoc")
    public ResponseEntity<Doctor> addDoctor(@Valid  @RequestBody DoctorRequest request){

        Doctor addDoc = doctorService.addDoctor(request);

        return new ResponseEntity<>(addDoc, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/alldoctors")
    public ResponseEntity<List<Doctor>> listAllDoctors(){

        List<Doctor> allDoctors = doctorService.getAllDoctors();

        return new ResponseEntity<>(allDoctors, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorbyId(@PathVariable Long id){

        Doctor doctorById = doctorService.getDoctorById(id);

        return new ResponseEntity<>(doctorById, HttpStatus.OK);
    }
//    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
//    @GetMapping("/{doctorname}")
//    public ResponseEntity<Doctor> getDoctorByName(@PathVariable String doctorname){
//
//        Doctor doctorByName = doctorService.getDoctorByName(doctorname);
//
//        return new ResponseEntity<>(doctorByName, HttpStatus.CREATED);
//    }
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(
            @RequestParam(required = false) String  doctorname,
            @RequestParam(required = false) Specialization specialization){

        if(doctorname!=null){
            return ResponseEntity.ok(List.of(doctorService.getDoctorByName(doctorname)));
        }
        if(specialization != null){
            return ResponseEntity.ok(doctorService.getDoctorBySpecialization(specialization));
        }

       return ResponseEntity.badRequest().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PutMapping("/{id}/availability")
    public Doctor updateAvailability( @PathVariable Long id,
                                      @RequestParam LocalTime from,
                                      @RequestParam LocalTime to) {
        return doctorService.updateAvailability( id, from, to );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteDoctor( @PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "Doctor Deleted Successfully";
    }
}
