package com.hospitalmanagement.authService.service;

import com.hospitalmanagement.authService.dto.AuthResponse;
import com.hospitalmanagement.authService.dto.LoginRequest;
import com.hospitalmanagement.authService.dto.SignUpRequest;
import com.hospitalmanagement.authService.entity.User;
import com.hospitalmanagement.authService.repository.UserRepository;
import com.hospitalmanagement.authService.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

//    public String signup(SignUpRequest request){
//
//        if(userRepository.existsByUsername(request.getUsername())){
//            throw new RuntimeException("username already exists");
//        }
//        User user = User.builder().username(request.getUsername())
//                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
//                .role(request.getRole()).enabled(true).build();
//            userRepository.save(user);
//            return "User signup successful";
//    }
//    public AuthResponse login(LoginRequest request){
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUsername(), request.getPassword()
//                )
//        );
//        String token = jwtUtil.generateToken(request.getUsername());
//        return new AuthResponse(token);
//    } //old code until here

    //new code start here

    public AuthResponse signup(SignUpRequest request){
        var user = User.builder().username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()).build();
        userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(user);
        var refreshToken = jwtUtil.generateRefresh(new HashMap<>(), user);
        return AuthResponse.builder().token(jwtToken).refreshToken(refreshToken)
                .build();
    }
    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwtToken = jwtUtil.generateToken(user);
        var refreshToken = jwtUtil.generateRefresh(new HashMap<>(), user);
        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthResponse refreshToken(String refreshToken) {

        var user = userRepository.findByUsername(jwtUtil.getEmailFromToken(refreshToken)).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        var jwtToken = jwtUtil.generateToken(user);
        var newRefreshToken = jwtUtil.generateRefresh(new HashMap<>(), user);
        return AuthResponse.builder()
                .token(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }

//    public Boolean validateToken(String token) {
//        return jwtUtil.validateToken(token);
//    }
}
