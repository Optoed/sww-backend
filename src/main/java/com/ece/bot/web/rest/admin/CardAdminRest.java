package com.ece.bot.web.rest.admin;


import com.ece.bot.dto.card.req.CreateCardDto;
import com.ece.bot.dto.card.res.CardDtoForAdmin;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/card")
public class CardAdminRest {
    @Autowired
    private CardService cardService;

    @PostMapping(value = "/create")
    public BotResponse<CardDtoForAdmin> createCard(@RequestBody CreateCardDto dto){
        return cardService.createNewCard(dto);
    }

    @DeleteMapping(value = "/delete")
    public BotResponse<?> deleteCard(@RequestParam(name = "cardId") Long cardId){
        return cardService.deleteCard(cardId);
    }

    @GetMapping(value = "/getAll")
    public BotResponse<List<CardDtoForAdmin>> getAllCards(){
        return cardService.getAllCardsForAdmin();
    }

}
