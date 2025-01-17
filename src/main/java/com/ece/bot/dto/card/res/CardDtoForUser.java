package com.ece.bot.dto.card.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDtoForUser {
    private Long id;
    private String imagePath;
    private Boolean isPurchased;
    private Integer currentLevel;
    private Boolean hasNextLevel;
    private Double priceForUpdate; //при наличии след уровня
    private Double powerOfNextLevel;
    private Double currentPower;
    private String name; // на языке пользователя
    private String description; // на языке пользователя

    @JsonIgnore
    private Boolean active;
}
