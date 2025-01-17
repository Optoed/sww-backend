package com.ece.bot.service;

import com.ece.bot.common.Language;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.user.res.UserInfoDto;

public interface SettingsService {
    public BotResponse<UserInfoDto> setNewLanguage(Language language);
}
