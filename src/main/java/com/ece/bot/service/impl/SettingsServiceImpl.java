package com.ece.bot.service.impl;

import com.ece.bot.common.Language;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.user.res.UserInfoDto;
import com.ece.bot.helper.CurrentUserInfo;
import com.ece.bot.mapper.UserMapper;
import com.ece.bot.model.User;
import com.ece.bot.repository.UserRepository;
import com.ece.bot.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsServiceImpl implements SettingsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public BotResponse<UserInfoDto> setNewLanguage(Language language) {
        BotResponse<UserInfoDto> response = new BotResponse<>();
        User user = CurrentUserInfo.getCurrentUser();
        user.setLanguage(language);
        user = userRepository.save(user);
        response.setData(userMapper.mapToDto(user));
        return response;
    }
}
