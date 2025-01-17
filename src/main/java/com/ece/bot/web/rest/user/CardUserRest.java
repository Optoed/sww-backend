package com.ece.bot.web.rest.user;

import com.ece.bot.dto.card.res.CardDtoForUser;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardUserRest {
    @Autowired
    private CardService cardService;

    @PostMapping(value = "/getAll")
    public BotResponse<List<CardDtoForUser>> getAllCards() {
        return cardService.getAllCards();
    }

    @PostMapping(value = "/buy")
    public BotResponse<CardDtoForUser> buyCard(@RequestParam(name = "cardId") Long cardId){
        return cardService.buyCard(cardId);
    }

    @PostMapping(value = "/upgrade")
    public BotResponse<CardDtoForUser> upgradeToNextLevel(@RequestParam(name = "cardId") Long cardId){
        return cardService.upgradeCardToNextLevel(cardId);
    }
}
