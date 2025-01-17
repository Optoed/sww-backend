package com.ece.bot.dto.system;

import com.ece.bot.common.error.ResponseError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BotResponse<T> {
    private Boolean success = true;
    private List<ResponseError> responseErrors;
    private T data;

    public void addError(ResponseError error){
        if(CollectionUtils.isEmpty(this.responseErrors)){
            this.responseErrors = new ArrayList<>();
        }
        success = false;
        responseErrors.add(error);
    }
}
