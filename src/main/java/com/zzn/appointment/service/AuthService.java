package com.zzn.appointment.service;


import com.zzn.appointment.common.BusinessException;
import com.zzn.appointment.dao.mapper.UserMapper;
import com.zzn.appointment.util.JwtUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;

    @Resource
    private UserMapper userMapper;

    public String login(String phone, String code) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }

        if (code == null || !code.equals("123456")) {
            throw new IllegalArgumentException("验证码错误");
        }

        Long userId = userMapper.findIdByPhone(phone);
        if (userId == null) {
            throw new BusinessException("用户不存在");
        }

        return jwtUtil.generateToken(String.valueOf(userId));
    }

    public Long parseToken(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException("Token 无效");
        }
        if (jwtUtil.isTokenExpired(token)) {
            throw new BusinessException("Token 已过期");
        }
        String userId = jwtUtil.getUserIdFromToken(token);
        return Long.valueOf(userId);
    }
}
