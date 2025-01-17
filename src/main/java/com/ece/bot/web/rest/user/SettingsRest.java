package com.ece.bot.web.rest.user;


import com.ece.bot.common.Language;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.user.res.UserInfoDto;
import com.ece.bot.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/settings")
public class SettingsRest {
    @Autowired
    private SettingsService settingsService;

    @PostMapping(value = "/language")
    public BotResponse<UserInfoDto> setLanguage(@RequestParam(name = "language") Language language){
        return settingsService.setNewLanguage(language);
    }

    @PostMapping(value = "/exchange")
    public BotResponse<UserInfoDto> setExchange(@RequestParam(name = "exchange") String exchange){
        return null;
    }
}
