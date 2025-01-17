package com.ece.bot.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest<T> {
    private WebAppData web_app_data;
    private T data;
}
