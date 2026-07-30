package com.hospitalmanagement.PatientService.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.http.HttpRequest;

@Configuration
@RequiredArgsConstructor
public class FeignInterceptor {

    @Bean
    public RequestInterceptor requestInterceptor(){

        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(attributes != null){
                HttpServletRequest request = attributes.getRequest();

                String authorization = request.getHeader("Authorization");

                if(authorization != null){

                        requestTemplate.header("Authorization", authorization);
                }
            }
        };
    }
}
