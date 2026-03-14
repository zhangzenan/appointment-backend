package com.zzn.appointment.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentRequest {
    private String serviceName;

    private LocalDate date;

    private String timeSlot;
}
