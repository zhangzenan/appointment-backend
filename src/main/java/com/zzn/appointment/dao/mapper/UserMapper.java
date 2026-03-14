package com.zzn.appointment.dao.mapper;

import com.zzn.appointment.dao.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    // 定义结果映射（可复用的映射规则）
    @Select("SELECT id, phone, created_at FROM users ORDER BY created_at DESC")
    @Results(id = "userResultMap", value = {
            @Result(property = "id", column = "id"),           // 数据库列 id → Java 属性 id
            @Result(property = "phone", column = "phone"),     // 数据库列 phone → Java 属性 phone
            @Result(property = "createdAt", column = "created_at") // 数据库列 created_at → Java 属性 createdAt
    })
    List<User> findAll();

    /**
     * 根据手机号查询用户
     */
    @Select("SELECT id, phone, created_at FROM users WHERE phone = #{phone}")
    @ResultMap("userResultMap")
    User findByPhone(String phone);

    /**
     * 根据手机号查询用户 ID
     */
    @Select("SELECT id FROM users WHERE phone = #{phone}")
    Long findIdByPhone(String phone);
}
