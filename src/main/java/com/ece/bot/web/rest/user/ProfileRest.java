package com.ece.bot.web.rest.user;

import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.system.UserRequest;
import com.ece.bot.dto.user.res.UserInfoDto;
import com.ece.bot.service.UserService;
import com.ece.bot.web.utils.ThreadRequestBodyPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/profile")
public class ProfileRest {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/deleteMyProfile")
    public BotResponse<UserInfoDto> delete() {
        return userService.deleteCurrentProfile();
    }

    @PostMapping(value = "/myProfile", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BotResponse<UserInfoDto> get() {
        UserRequest<?> userRequest = ThreadRequestBodyPool.getBody();
        return userService.getCurrentProfile();
    }
}
