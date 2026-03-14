package com.zzn.appointment.web;

import com.zzn.appointment.dto.ApiResponse;
import com.zzn.appointment.dto.LoginRequest;
import com.zzn.appointment.dto.LoginResponse;
import com.zzn.appointment.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getPhone(), request.getCode());
        LoginResponse response = new LoginResponse(token, request.getPhone());
        return ApiResponse.success(response);
    }
}
