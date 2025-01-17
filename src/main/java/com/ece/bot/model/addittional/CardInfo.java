package com.ece.bot.model.addittional;

import com.ece.bot.common.Language;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardInfo {
    private Map<Language,NameAndDescription> languageNameAndDescriptionMap;
}
