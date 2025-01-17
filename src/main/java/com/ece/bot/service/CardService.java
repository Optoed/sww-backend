package com.ece.bot.service;

import com.ece.bot.dto.card.req.CreateCardDto;
import com.ece.bot.dto.card.res.CardDtoForAdmin;
import com.ece.bot.dto.card.res.CardDtoForUser;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.model.Card;

import java.util.List;

public interface CardService {
    public BotResponse<CardDtoForUser> buyCard(Long id);
    public BotResponse<CardDtoForUser> upgradeCardToNextLevel(Long id);
    public BotResponse<List<CardDtoForUser>> getAllCards();

    List<Card> getStartCards();

    public BotResponse<CardDtoForAdmin> createNewCard(CreateCardDto dto);
    public BotResponse<?> deleteCard(Long cardId);
    public BotResponse<List<CardDtoForAdmin>> getAllCardsForAdmin();
}
