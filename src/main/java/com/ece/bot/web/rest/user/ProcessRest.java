package com.ece.bot.web.rest.user;


import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.user.res.UserInfoForAdminDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/process")
public class ProcessRest {
    @PostMapping(value = "/start")
    public BotResponse<UserInfoForAdminDto> start() {
        return null; //TODO
    }

    @PostMapping(value = "/claim")
    public BotResponse<UserInfoForAdminDto> claim() {
        return null; //TODO
    }
}
