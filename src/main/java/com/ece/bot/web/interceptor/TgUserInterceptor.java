package com.ece.bot.web.interceptor;

import com.ece.bot.common.http.PARAMS;
import com.ece.bot.dto.system.UserRequest;
import com.ece.bot.dto.system.WebAppData;
import com.ece.bot.dto.user.res.UserInfoDto;
import com.ece.bot.helper.CurrentUserInfo;
import com.ece.bot.service.UserService;
import com.ece.bot.web.utils.CachedBodyHttpServletRequest;
import com.ece.bot.web.utils.ThreadRequestBodyPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class TgUserInterceptor implements HandlerInterceptor {

    @Value("${app.auth.conf.max-minutes}")
    private long maxMinutesAuth;
    @Value("${app.auth.conf.time-validation}")
    private Boolean timeValidation;
    @Value("${app.conf.bot.token}")
    private String botToken;


    @Autowired
    private UserService userService;




    // Validate auth date to prevent using data older than 24 hours (custom)
    public boolean validateAuthDate(String authDate) {
        try {
            long authDateInt = Long.parseLong(authDate);
            Date authTime = new Date(authDateInt * 1000); // Convert to milliseconds
            return (new Date().getTime() - authTime.getTime()) <= TimeUnit.MINUTES.toMillis(maxMinutesAuth);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing auth_date: " + e.getMessage());
            return false;
        }
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//       request.getInputStream().close();
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
        String inviteCode = request.getParameter(PARAMS.INVITE.getValue());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData;
        try  {
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            BufferedReader reader = cachedBodyHttpServletRequest.getReader();
            jsonData = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
        }catch (Exception e){
            return false;
        }
        // Преобразование JSON в объект
        UserRequest<?> userRequest = objectMapper.readValue(jsonData, UserRequest.class);
        ThreadRequestBodyPool.setBody(userRequest);
        com.ece.bot.dto.system.WebAppData webAppData = userRequest.getWeb_app_data();

        String receivedHash = webAppData.getHash();
        String authDate = webAppData.getAuth_date();
        String dataCheckString = webAppData.getData_check_string();

        // Validate Telegram data
        if (!AuthHelper.isValid(dataCheckString,botToken, receivedHash)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Request not validated");
            return false;
        }else{
            if(timeValidation != null && timeValidation && !validateAuthDate(authDate)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Request not validated");
                return false;
            }
            try {
                UserInfoDto userInfoDto = null;
                WebAppData.UserWebAppData data = WebAppData.getUserData(dataCheckString);
                if(data == null || data.id == null){
                    return false;
                }
                userInfoDto = userService.getUserByTgId(data.id,true, true);
                if(userInfoDto == null){
                    userInfoDto = userService.registerNewUser(data, inviteCode);
                }
                CurrentUserInfo.setCurrentUserInfo(userInfoDto);
            }catch (Exception ex){
                return false;
            }
        }
        return true;
    }
}
