package com.ece.bot.service.impl;

import com.ece.bot.helper.CurrentUserInfo;
import com.ece.bot.model.Card;
import com.ece.bot.model.CardInstance;
import com.ece.bot.model.User;
import com.ece.bot.repository.CardRepository;
import com.ece.bot.repository.InstanceRepository;
import com.ece.bot.service.CardService;
import com.ece.bot.service.InstanceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class InstanceServiceImpl implements InstanceService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private InstanceRepository instanceRepository;

    @Override
    public void setStartCardsForNewUser() {
        User user = CurrentUserInfo.getCurrentUser();
        List<Card> startCards = cardRepository.findByIsStartCard(true);
        if (!CollectionUtils.isEmpty(startCards)) {
            startCards.forEach(card -> {
                createNewInstance(card, user);
            });
        }
    }

    @Override
    public void setCardForUser(Card card) {
        User user = CurrentUserInfo.getCurrentUser();
        createNewInstance(card, user);
    }


    private CardInstance createNewInstance(Card card, User user) {
        CardInstance instance = new CardInstance();
        instance.setCard(card);
        instance.setUser(user);
        instance.setLevel(1);
        instance.setPower(card.getCardLevelMap().getCardLevelMap().get(1).power);
        instance = instanceRepository.save(instance);
        return instance;
    }

    @Override
    public void updateInstance(Card card, Double newPower) {
        User user = CurrentUserInfo.getCurrentUser();
        List<CardInstance> instances = instanceRepository.findByUserAndCardIn(user, Collections.singletonList(card));
        if (!CollectionUtils.isEmpty(instances)) {
            CardInstance instance = instances.get(0);
            instance.setLevel(instance.getLevel() + 1);
            instance.setPower(newPower);
            instance = instanceRepository.save(instance);
        }
    }

    @Override
    public void updateInstance(CardInstance instance, Double power) {
        instance.setLevel(instance.getLevel() + 1);
        instance.setPower(power);
        instance = instanceRepository.save(instance);
    }

    @Override
    public CardInstance getInstance(Card card) {
        User user = CurrentUserInfo.getCurrentUser();
        List<CardInstance> instances = instanceRepository.findByUserAndCardIn(user, Collections.singletonList(card));
        if (!CollectionUtils.isEmpty(instances)) {
            return instances.get(0);
        } else {
            return null;
        }
    }
}
