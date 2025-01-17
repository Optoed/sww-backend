package com.ece.bot.service.impl;

import com.ece.bot.common.EventType;
import com.ece.bot.common.Language;
import com.ece.bot.common.UserState;
import com.ece.bot.common.error.ResponseError;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.system.WebAppData;
import com.ece.bot.dto.user.res.UserInfoDto;
import com.ece.bot.dto.user.res.UserInfoForAdminDto;
import com.ece.bot.helper.CurrentUserInfo;
import com.ece.bot.mapper.UserMapper;
import com.ece.bot.model.User;
import com.ece.bot.repository.UserRepository;
import com.ece.bot.service.BalanceService;
import com.ece.bot.service.InstanceService;
import com.ece.bot.service.StatisticService;
import com.ece.bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BalanceService balanceService;
    @Autowired
    private InstanceService instanceService;
    @Autowired
    private StatisticService statisticService;

    @Override
    public UserInfoDto getUserByTgId(String tgId, Boolean onlyActive, Boolean setToContext) {

        Optional<User> optionalUser = null;
        if (onlyActive) {
            optionalUser = userRepository.findByTgChatIdAndStateIn(tgId, Collections.singletonList(UserState.ACTIVE));
        } else {
            optionalUser = userRepository.findByTgChatIdAndStateIn(tgId, Arrays.asList(UserState.values()));
        }
        if (setToContext && optionalUser.isPresent()) {
            if(optionalUser.get().getId() != null){
                CurrentUserInfo.setCurrentUser(optionalUser.get());
            }
        }
        return optionalUser.map(user -> userMapper.mapToDto(user)).orElse(null);
    }

    @Override
    public UserInfoDto registerNewUser(WebAppData.UserWebAppData webAppData, String inviteCode) {
        User user = new User();
        user.setName(webAppData.first_name + " " + webAppData.username);
        user.setWelcomeDate(LocalDateTime.now());
        user.setState(UserState.ACTIVE);
        user.setLanguage(Language.getLanguageByCode(webAppData.language_code));
        user.setTgChatId(webAppData.id);
        user.setReferalCode(generateReferalCode());
        user = userRepository.save(user);
        CurrentUserInfo.setCurrentUser(user);
        balanceService.createBalanceForNewUser(user);
        instanceService.setStartCardsForNewUser();
        statisticService.pushNewEvent(EventType.NEW_USER_REGISTRATION, null, user.getId().toString());
        if(inviteCode != null && !inviteCode.isEmpty()){
            Optional<User> optionalUser = userRepository.findByReferalCode(inviteCode);
            if(optionalUser.isPresent()){
                User inviteUser = optionalUser.get();
                if(CollectionUtils.isEmpty(inviteUser.getReferals())){
                    inviteUser.setReferals(new ArrayList<>());
                }
                inviteUser.getReferals().add(user);
                userRepository.save(inviteUser);
            }
        }
        return userMapper.mapToDto(user);
    }

    private String generateReferalCode(){
        return UUID.randomUUID().toString() +
                UUID.randomUUID().toString() +
                UUID.randomUUID().toString() +
                UUID.randomUUID().toString() +
                UUID.randomUUID().toString() +
                UUID.randomUUID().toString();
    }

    @Override
    public BotResponse<UserInfoDto> getCurrentProfile() {
        BotResponse<UserInfoDto> response = new BotResponse<>();
        response.setData(CurrentUserInfo.getCurrentUserInfo());
        return response;
    }

    @Override
    public BotResponse<UserInfoDto> deleteCurrentProfile() {
        BotResponse<UserInfoDto> response = new BotResponse<>();
        User user = CurrentUserInfo.getCurrentUser();
        user.setState(UserState.DELETED);
        user = userRepository.save(user);
        response.setData(userMapper.mapToDto(user));
        return response;
    }

    @Override
    public BotResponse<UserInfoForAdminDto> changeUserState(Long id, UserState state) {
        BotResponse<UserInfoForAdminDto> response = new BotResponse<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            response.addError(ResponseError.USER_NOT_FOUND);
            return response;
        } else {
            User user = optionalUser.get();
            if (user.getState().equals(UserState.DELETED)) {
                response.addError(ResponseError.INVALID_USER_STATE_MOVE);
                return response;
            }
            user.setState(state);
            user = userRepository.save(user);
            response.setData(userMapper.mapToAdminDto(user));
            return response;
        }
    }
}
