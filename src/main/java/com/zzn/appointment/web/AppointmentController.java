package com.zzn.appointment.web;


import com.zzn.appointment.dao.entity.Appointment;
import com.zzn.appointment.dto.ApiResponse;
import com.zzn.appointment.dto.AppointmentRequest;
import com.zzn.appointment.dto.LoginResponse;
import com.zzn.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * 创建预约
     */
    @PostMapping("/create")
    public ApiResponse<Boolean> create(
            @RequestAttribute("userId") Long userId,
            @RequestBody AppointmentRequest request
    ) {
        boolean result = appointmentService.createAppointment(userId, request);

        return result ? ApiResponse.success(true) : ApiResponse.error(400, "创建预约失败");
    }

    /**
     * 我的预约
     */
    @GetMapping("/list")
    public ApiResponse<List<Appointment>> list(
            @RequestAttribute("userId") Long userId
    ) {
        List<Appointment> appointments = appointmentService.listAppointments(userId);

        return ApiResponse.success(appointments);
    }
}