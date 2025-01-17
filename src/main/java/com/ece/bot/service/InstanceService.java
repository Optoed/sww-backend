package com.ece.bot.service;

import com.ece.bot.model.Card;
import com.ece.bot.model.CardInstance;
import com.ece.bot.model.User;

public interface InstanceService {
    public void setStartCardsForNewUser();
    public void setCardForUser(Card card);
    public void updateInstance(Card card, Double newPower);
    public void updateInstance(CardInstance instance, Double power);
    public CardInstance getInstance(Card card);
}
