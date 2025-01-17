package com.ece.bot.dto.user.req;

import com.ece.bot.common.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
//поиск пользователей в админке
public class SearchUserDto {
    private List<UserState> states;
    private Double minBalance;
    private Double maxBalance;
    private LocalDateTime minWelcomeDate;
    private LocalDateTime maxWelcomeDate;
    private String query;
}
