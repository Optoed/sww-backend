package com.ece.bot.web.utils;

import com.ece.bot.dto.system.UserRequest;

public class ThreadRequestBodyPool {
    private static final ThreadLocal<UserRequest<?>> threadBody = new ThreadLocal<>();
    public static void setBody(UserRequest<?> userRequest) {
        threadBody.set(userRequest);
    }
    public static UserRequest<?> getBody() {
        return threadBody.get();
    }
}
