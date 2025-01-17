package com.ece.bot.helper;

import com.ece.bot.dto.user.res.UserInfoDto;
import com.ece.bot.model.User;

public class CurrentUserInfo {
    private static ThreadLocal<UserInfoDto> currentUser = new ThreadLocal<>();
    private static ThreadLocal<User> userModel = new ThreadLocal<>();
    private static ThreadLocal<String> ip = new ThreadLocal<>();

    public static UserInfoDto getCurrentUserInfo() {
        return currentUser.get();
    }

    public static void setCurrentUserInfo(UserInfoDto user){
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return userModel.get();
    }

    public static void setCurrentUser(User user){
        userModel.set(user);
    }

    public static String getIp(){
        return ip.get();
    }

    public static void setIp(String ipAddress){
        ip.set(ipAddress);
    }
}
