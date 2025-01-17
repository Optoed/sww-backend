package com.ece.bot.mapper;

import com.ece.bot.common.CardState;
import com.ece.bot.dto.card.res.CardDtoForAdmin;
import com.ece.bot.dto.card.res.CardDtoForUser;
import com.ece.bot.helper.CurrentUserInfo;
import com.ece.bot.model.Card;
import com.ece.bot.model.CardInstance;
import com.ece.bot.repository.InstanceRepository;
import com.ece.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CardMapper {
    @Autowired
    private InstanceRepository instanceRepository;


    public CardDtoForAdmin mapToAdminDto(Card card){
        CardDtoForAdmin dto = new CardDtoForAdmin();
        dto.setId(card.getId());
        dto.setCardInfo(card.getCardInfo());
        dto.setCardLevelMap(card.getCardLevelMap());
        dto.setCodeName(card.getCodeName());
        if(card.getImage() != null){
            dto.setImagePath(card.getImage().getFileName());
        }
        return dto;
    }

    public List<CardDtoForAdmin> mapToAdminDto(List<Card> cards){
        List<CardDtoForAdmin> dtos = new ArrayList<>();
        cards.forEach(card -> {
            dtos.add(mapToAdminDto(card));
        });
        return dtos;
    }

    public List<CardDtoForUser> mapToUserDto(List<Card> cards){
        List<CardDtoForUser> dtos = new ArrayList<>();
        List<CardInstance> instances = instanceRepository.findByUserAndCardIn(CurrentUserInfo.getCurrentUser(),cards);
        cards.forEach(card -> {
            CardDtoForUser dto = new CardDtoForUser();
            dto.setId(card.getId());
            if(card.getImage() != null){
                dto.setImagePath(card.getImage().getFileName());
            }
            Optional<CardInstance> optionalInstance = instances.stream().filter(i -> card.getId().equals(i.getCard().getId())).findAny();
            if(optionalInstance.isPresent()){
                CardInstance i = optionalInstance.get();
                dto.setCurrentLevel(i.getLevel());
                dto.setCurrentPower(card.getCardLevelMap().getCardLevelMap().get(i.getLevel()).power);
                dto.setHasNextLevel(card.getMaxLevel() > i.getLevel());
                if(dto.getHasNextLevel()){
                    dto.setPowerOfNextLevel(card.getCardLevelMap().getCardLevelMap().get(i.getLevel() + 1).power);
                    dto.setPriceForUpdate(card.getCardLevelMap().getCardLevelMap().get(i.getLevel() + 1).levelPrice);
                }
                dto.setIsPurchased(true);
            }else{
                dto.setCurrentLevel(0);
                dto.setIsPurchased(false);
                dto.setPowerOfNextLevel(card.getCardLevelMap().getCardLevelMap().get(1).power);
                dto.setPriceForUpdate(card.getCardLevelMap().getCardLevelMap().get(1).levelPrice);
            }
            dto.setActive(card.getState().equals(CardState.ACTIVE));
            dto.setDescription(card.
                    getCardInfo().getLanguageNameAndDescriptionMap().
                    get(CurrentUserInfo.getCurrentUser().getLanguage()).description);

            dto.setName(card.
                    getCardInfo().getLanguageNameAndDescriptionMap().
                    get(CurrentUserInfo.getCurrentUser().getLanguage()).name);

            dtos.add(dto);
        });
        List<CardDtoForUser> filtered = dtos.stream().filter(card -> (card.getIsPurchased() || card.getActive())).collect(Collectors.toList());
        return filtered;
    }

    public CardDtoForUser mapToUserDto(Card card){
        return this.mapToUserDto(Collections.singletonList(card)).get(0);
    }
}
