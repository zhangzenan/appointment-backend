package com.zzn.appointment.filter;


import com.zzn.appointment.service.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenFilter implements Filter {

    @Autowired
    private AuthService authService;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest http = (HttpServletRequest) request;

        String path = http.getRequestURI();

        // 登录接口不拦截
        if (path.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        String auth = http.getHeader("Authorization");

        if (auth != null && auth.startsWith("Bearer ")) {

            String token = auth.substring(7);

            Long userId = authService.parseToken(token);

            request.setAttribute("userId", userId);
        }

        chain.doFilter(request, response);
    }
}
