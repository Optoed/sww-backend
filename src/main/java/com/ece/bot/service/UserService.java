package com.ece.bot.service;

import com.ece.bot.common.UserState;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.system.WebAppData;
import com.ece.bot.dto.user.res.UserInfoDto;
import com.ece.bot.dto.user.res.UserInfoForAdminDto;

public interface UserService {
    public UserInfoDto getUserByTgId(String tgId,Boolean onlyActive, Boolean setContext);
    public UserInfoDto registerNewUser(WebAppData.UserWebAppData webAppData, String inviteCode);
    public BotResponse<UserInfoDto> getCurrentProfile();
    public BotResponse<UserInfoDto> deleteCurrentProfile();
    public BotResponse<UserInfoForAdminDto> changeUserState(Long id, UserState state);
}
