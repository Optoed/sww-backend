package com.ece.bot.web.rest.admin;

import com.ece.bot.common.UserState;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.user.req.SearchUserDto;
import com.ece.bot.dto.user.res.UserInfoForAdminDto;
import com.ece.bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserRest {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/search")
    public BotResponse<List<UserInfoForAdminDto>> search(@RequestBody SearchUserDto dto) {
        return null; //TODO
    }

    @GetMapping(value = "/changeState")
    public BotResponse<UserInfoForAdminDto> changeState(@RequestParam(name = "userId") Long userId, @RequestParam(name = "state") UserState state) {
        return userService.changeUserState(userId,state);
    }
}
