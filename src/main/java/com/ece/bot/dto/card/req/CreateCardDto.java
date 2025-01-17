package com.ece.bot.dto.card.req;

import com.ece.bot.model.addittional.CardInfo;
import com.ece.bot.model.addittional.CardLevelMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCardDto {
    private String name;
    private CardInfo cardInfo;
    private Long imageId;
    private CardLevelMap cardLevelMap;
    private Boolean isStartCard;
}
