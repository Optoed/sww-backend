package com.ece.bot.validation;

import com.ece.bot.common.Language;
import com.ece.bot.common.error.ResponseError;
import com.ece.bot.dto.card.req.CreateCardDto;
import com.ece.bot.dto.card.res.CardDtoForAdmin;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.model.Card;
import com.ece.bot.model.CardFile;
import com.ece.bot.model.addittional.CardInfo;
import com.ece.bot.model.addittional.CardLevelMap;
import com.ece.bot.model.addittional.CardParameters;
import com.ece.bot.model.addittional.NameAndDescription;
import com.ece.bot.repository.CardRepository;
import com.ece.bot.service.FileService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class CardValidator {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private FileService fileService;

    public BotResponse<CardDtoForAdmin> validateCreation(CreateCardDto dto) {
        BotResponse<CardDtoForAdmin> response = new BotResponse<>();
        if (dto.getIsStartCard() == null) {
            dto.setIsStartCard(false);
        }
        if (dto.getImageId() == null) {
            response.addError(ResponseError.EMPTY_CARD_NAME);
        } else {
            CardFile file = fileService.getById(dto.getImageId());
            if (file == null) {
                response.addError(ResponseError.FILE_NOT_FOUND);
            }
        }
        if (dto.getName() == null || dto.getName().isEmpty()) {
            response.addError(ResponseError.EMPTY_CARD_NAME);
        } else {
            Optional<Card> optionalCard = cardRepository.findByCodeName(dto.getName());
            if (optionalCard.isPresent()) {
                response.addError(ResponseError.NOT_UNIQUE_CARD_NAME);
            }
        }
        CardInfo info = dto.getCardInfo();
        if (info == null) {
            response.addError(ResponseError.EMPTY_CARD_INFO);
        } else {
            Map<Language, NameAndDescription> map = info.getLanguageNameAndDescriptionMap();
            if (map == null) {
                response.addError(ResponseError.EMPTY_CARD_INFO);
            } else {
                Set<Language> set = map.keySet();
                boolean isAll = set.containsAll(Set.of(Language.values()));
                if (!isAll) {
                    response.addError(ResponseError.NOT_ALL_LANGUAGES_IN_INFO);
                } else {
                    Collection<NameAndDescription> texts = map.values();
                    texts.forEach(t -> {
                        if (t == null) {
                            response.addError(ResponseError.EMPTY_NAME_AND_DESCRIPTION);
                        } else {
                            if (t.name == null || t.name.isEmpty()) {
                                response.addError(ResponseError.EMPTY_NAME_IN_LANGUAGES_MAP);
                            }
                            if (t.description == null || t.description.isEmpty()) {
                                response.addError(ResponseError.EMPTY_DESCRIPTION_IN_LANGUAGES_MAP);
                            }
                        }
                    });
                }
            }
        }

        CardLevelMap levelMap = dto.getCardLevelMap();
        if (levelMap == null) {
            response.addError(ResponseError.EMPTY_CARD_LEVEL_MAP);
        } else {
            Integer max = levelMap.getMaxLevel();
            if (max == null) {
                response.addError(ResponseError.EMPTY_MAX_LEVEL);
            } else {
                Map<Integer, CardParameters> map = levelMap.getCardLevelMap();
                if (map == null) {
                    response.addError(ResponseError.EMPTY_CARD_LEVEL_MAP);
                } else {
                    Set<Integer> levels = map.keySet();
                    if (!levels.contains(max)) {
                        response.addError(ResponseError.CARD_LEVEL_MAP_NOT_CONTAINS_MAX_LEVEL);
                    }
                    Optional<Integer> errorValue = levels.stream().filter(v -> v.equals(0) || v < 0)
                            .findAny();
                    if (errorValue.isPresent()) {
                        response.addError(ResponseError.ZERO_OR_NEGATIVE_LEVELS);
                    } else {
                        Integer summLevel = ((1 + max) * max) / 2;
                        AtomicReference<Integer> controlLevel = new AtomicReference<>(0);
                        levels.forEach(l -> controlLevel.updateAndGet(v -> v + l));
                        if (!summLevel.equals(controlLevel.get())) {
                            response.addError(ResponseError.INVALID_LEVELS_SEQUENCE);
                        } else {
                            Collection<CardParameters> params = map.values();
                            params.forEach(p -> {
                                if (p.power == null) {
                                    response.addError(ResponseError.EMPTY_CARD_POWER);
                                } else if (p.power < 0) {
                                    response.addError(ResponseError.NEGATIVE_CARD_POWER);
                                } else if (p.levelPrice == null) {
                                    response.addError(ResponseError.EMPTY_CARD_PRICE);
                                } else if (p.levelPrice < 0) {
                                    response.addError(ResponseError.NEGATIVE_CARD_PRICE);
                                }
                            });
                        }
                    }
                }
            }
        }
        return response;
    }

}
