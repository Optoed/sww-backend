package com.ece.bot.dto.karma.res;


import com.ece.bot.common.KarmaCategory;
import com.ece.bot.common.KarmaState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KarmaCardDto {
    private Integer id;
    private String title;
    private String description;
    private String imagePath;
    private Boolean isPurchased;
    private Double minPrice;
    private Double goal;
    private Double raised;
    private Double userDonat;
    private KarmaState state;
    private Double power;
    private KarmaCategory category;
}
