package com.ece.bot.web.interceptor;

import com.ece.bot.dto.system.UserRequest;
import com.ece.bot.dto.system.WebAppData;
import com.ece.bot.helper.CurrentUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class IpAdressDetectorInterceptor implements HandlerInterceptor {


    public static String getClientIP(HttpServletRequest request)  {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        } else {
            // Если в заголовке X-FORWARDED-FOR несколько IP-адресов, берем первый
            String[] ipAddresses = ipAddress.split(",");
            ipAddress = ipAddresses[0].trim();
        }

        return ipAddress;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = getClientIP(request);
        CurrentUserInfo.setIp(ip);
        return true;
    }
}
