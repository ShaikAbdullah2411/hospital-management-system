package com.hospitalmanagement.AppointmentService.config;

import com.hospitalmanagement.AppointmentService.entity.UserRole;
import com.hospitalmanagement.AppointmentService.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
//    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/webjars/**" )
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/appointments/**")
                        .hasAnyRole( UserRole.ADMIN.name(), UserRole.DOCTOR.name(), UserRole.PATIENT.name() )
                        .requestMatchers(HttpMethod.POST, "/appointments/**")
                        .hasRole(UserRole.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/appointments/**")
                        .hasAnyRole( UserRole.ADMIN.name(), UserRole.DOCTOR.name() )
                        .requestMatchers(HttpMethod.DELETE, "/appointments/**")
                        .hasRole(UserRole.ADMIN.name())
                        .anyRequest().authenticated() )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                        .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
