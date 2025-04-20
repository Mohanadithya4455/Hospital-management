package com.hospital.doctor;

import com.hospital.doctor.dto.AuthDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authentication-service", url = "http://localhost:8000")
public interface AuthService {
    @GetMapping("/auth/get_user")
    public AuthDto getUserDetails(@RequestHeader("Authorization") String jwt);
}
