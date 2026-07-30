package com.hospitalmanagement.authService.controller;
import com.hospitalmanagement.authService.dto.*;
import com.hospitalmanagement.authService.entity.User;
import com.hospitalmanagement.authService.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @GetMapping("/test")
//    public String test(){
//        return "Auth service working";
//    }
//
//
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequest request){
//
//        String response = authService.signup(request);
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//
//    }
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
//
//        AuthResponse dologin = authService.login(request);
//        return new ResponseEntity<>(dologin, HttpStatus.CREATED);
//    } //old code until here

    //new code from here
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody SignUpRequest request) {
        try {
            AuthResponse res = authService.signup(request);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> register(@RequestBody LoginRequest request) {
        try {
            AuthResponse res = authService.authenticate(request);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }
    @PostMapping("/create-doctor")
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody CreateDoctorRequest request){

        return ResponseEntity.ok(authService.createDoctor(request));
    }

    @PostMapping("/create-patient")
    public ResponseEntity<PatientResponse> createpatient(@RequestBody CreatePatientRequest request){

        return ResponseEntity.ok(authService.createPatient(request));
    }


    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestParam("token") String refreshToken) {
        try {
            AuthResponse res = authService.refreshToken(refreshToken);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

//    @GetMapping("/validateToken")
//    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
//        try {
//            Boolean res = authService.validateToken(token);
//            return ResponseEntity.ok(res);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}


