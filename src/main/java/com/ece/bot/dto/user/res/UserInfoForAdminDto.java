package com.ece.bot.dto.user.res;

import com.ece.bot.common.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoForAdminDto {
    private Long id;
    private String name;
    private Double balance;
    private LocalDateTime welcomeDate;
    private LocalDateTime lastVisit;
    private UserState state;
}
