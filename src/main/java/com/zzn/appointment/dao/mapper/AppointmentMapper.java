package com.zzn.appointment.dao.mapper;

import com.zzn.appointment.dao.entity.Appointment;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AppointmentMapper {
    /**
     * 新增预约
     */
    @Insert("INSERT INTO appointments (user_id, service_name, date, time_slot, created_at) " +
            "VALUES (#{userId}, #{serviceName}, #{date}, #{timeSlot}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Appointment appointment);

    /**
     * 查询所有预约列表
     */
    @Select("SELECT id, user_id, service_name, date, time_slot, created_at FROM appointments ORDER BY created_at DESC")
    @Results(id = "appointmentResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "serviceName", column = "service_name"),
            @Result(property = "date", column = "date"),
            @Result(property = "timeSlot", column = "time_slot"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<Appointment> findAll();

    /**
     * 根据用户 ID 查询预约列表
     */
    @Select("SELECT id, user_id, service_name, date, time_slot, created_at FROM appointments WHERE user_id = #{userId} ORDER BY created_at DESC")
    @ResultMap("appointmentResultMap")
    List<Appointment> findByUserId(Long userId);

    /**
     * 根据用户 ID、日期和时间段查询预约
     */
    @Select("SELECT id, user_id, service_name, date, time_slot, created_at FROM appointments " +
            "WHERE user_id = #{userId} AND date = #{date} AND time_slot = #{timeSlot}")
    @ResultMap("appointmentResultMap")
    Appointment findByUserIdAndDateAndTimeSlot(Long userId, LocalDate date, String timeSlot);

}
