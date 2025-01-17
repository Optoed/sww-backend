package com.ece.bot.dto.karma.req;

import com.ece.bot.common.KarmaCategory;
import com.ece.bot.model.addittional.CardInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateKarmaDto {
    private String name;
    private CardInfo karmaInfo;
    private Long imageId;
    private Double karmaGoal;
    private Double minDonate;
    private KarmaCategory category;
}
