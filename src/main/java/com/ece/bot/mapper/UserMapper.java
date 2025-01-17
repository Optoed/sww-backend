package com.ece.bot.mapper;

import com.ece.bot.dto.user.res.UserInfoDto;
import com.ece.bot.dto.user.res.UserInfoForAdminDto;
import com.ece.bot.model.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class UserMapper {
    public UserInfoDto mapToDto(User user){
        UserInfoDto dto = new UserInfoDto();
        dto.setBalance(user.getBalance().getAmount());
        dto.setName(user.getName());
        dto.setLanguage(user.getLanguage());
        return dto;
    }

    public UserInfoForAdminDto mapToAdminDto(User user){
        UserInfoForAdminDto dto = new UserInfoForAdminDto();
        dto.setBalance(user.getBalance().getAmount());
        dto.setName(user.getName());
        dto.setState(user.getState());
        dto.setLastVisit(null); //TODO сделать когда статистика будет готова
        dto.setWelcomeDate(user.getWelcomeDate());
        return dto;
    }

    public List<UserInfoForAdminDto> mapToAdminDto(List<User> users){
        List<UserInfoForAdminDto> dtos = new ArrayList<>();
        users.forEach(user -> {
            dtos.add(mapToAdminDto(user));
        });
        return dtos;
    }
}
