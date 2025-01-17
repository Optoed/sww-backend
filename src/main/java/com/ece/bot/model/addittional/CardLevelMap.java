package com.ece.bot.model.addittional;


import com.ece.bot.model.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardLevelMap {
    private Map<Integer, CardParameters> cardLevelMap;
    private Integer maxLevel;

    public Integer getMaxLevel(Card card) {
        return this.maxLevel;
    }


}
