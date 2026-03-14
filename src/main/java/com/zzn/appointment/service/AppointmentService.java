package com.zzn.appointment.service;

import com.zzn.appointment.common.BusinessException;
import com.zzn.appointment.dao.entity.Appointment;
import com.zzn.appointment.dao.mapper.AppointmentMapper;
import com.zzn.appointment.dto.AppointmentRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AppointmentService {
    @Resource
    private AppointmentMapper appointmentMapper;

    /**
     * 创建预约
     */
    public Boolean createAppointment(Long userId, AppointmentRequest request) {

        // 幂等判断
        Appointment exist = appointmentMapper
                .findByUserIdAndDateAndTimeSlot(userId, request.getDate(), request.getTimeSlot());
        if (exist != null) {
            throw new BusinessException("用户已预约过该时间段");
        }
        Appointment appointment = Appointment.builder()
                .userId(userId)
                .serviceName(request.getServiceName())
                .date(request.getDate())
                .timeSlot(request.getTimeSlot())
                .createdAt(LocalDateTime.now())
                .build();

        int insert = appointmentMapper.insert(appointment);

        return insert > 0;
    }

    /**
     * 查询我的预约
     */
    public List<Appointment> listAppointments(Long userId) {
        return appointmentMapper.findByUserId(userId);
    }
}
