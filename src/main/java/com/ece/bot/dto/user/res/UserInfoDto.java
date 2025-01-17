package com.ece.bot.dto.user.res;

import com.ece.bot.common.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoDto {
    private Language language;
    private String name;
    private Double balance;
    private Double newSpeed;
    private String chatId;
    private ProcessInfoDto processInfo;
}
