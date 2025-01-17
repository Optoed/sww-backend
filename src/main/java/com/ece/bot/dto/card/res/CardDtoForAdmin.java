package com.ece.bot.dto.card.res;


import com.ece.bot.model.addittional.CardInfo;
import com.ece.bot.model.addittional.CardLevelMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDtoForAdmin {
    private Long id;
    private String imagePath;
    private String codeName;
    private CardLevelMap cardLevelMap;
    private CardInfo cardInfo;
}
