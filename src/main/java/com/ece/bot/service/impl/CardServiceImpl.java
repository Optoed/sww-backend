package com.ece.bot.service.impl;

import com.ece.bot.common.CardState;
import com.ece.bot.common.Language;
import com.ece.bot.common.OperationType;
import com.ece.bot.common.error.ResponseError;
import com.ece.bot.dto.card.req.CreateCardDto;
import com.ece.bot.dto.card.res.CardDtoForAdmin;
import com.ece.bot.dto.card.res.CardDtoForUser;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.helper.CurrentUserInfo;
import com.ece.bot.mapper.CardMapper;
import com.ece.bot.model.Balance;
import com.ece.bot.model.Card;
import com.ece.bot.model.CardInstance;
import com.ece.bot.model.User;
import com.ece.bot.model.addittional.CardInfo;
import com.ece.bot.model.addittional.CardLevelMap;
import com.ece.bot.model.addittional.CardParameters;
import com.ece.bot.model.addittional.NameAndDescription;
import com.ece.bot.repository.CardRepository;
import com.ece.bot.repository.InstanceRepository;
import com.ece.bot.repository.UserRepository;
import com.ece.bot.service.BalanceService;
import com.ece.bot.service.CardService;
import com.ece.bot.service.FileService;
import com.ece.bot.service.InstanceService;
import com.ece.bot.validation.CardValidator;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private InstanceRepository instanceRepository;
    @Autowired
    private CardValidator cardValidator;
    @Autowired
    private FileService fileService;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private InstanceService instanceService;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private UserRepository userRepository;

    @Value("${app.conf.testing-mode}")
    private Boolean testingMode;

    @Override
    public List<Card> getStartCards() {
        return cardRepository.findByIsStartCard(true);
    }

    @Override
    public BotResponse<CardDtoForAdmin> createNewCard(CreateCardDto dto) {
        BotResponse<CardDtoForAdmin> response = cardValidator.validateCreation(dto);
        if (!response.getSuccess()) {
            return response;
        }
        Card card = new Card();
        card.setCardInfo(dto.getCardInfo());
        card.setIsStartCard(dto.getIsStartCard());
        card.setMaxLevel(dto.getCardLevelMap().getMaxLevel());
        card.setCardLevelMap(dto.getCardLevelMap());
        card.setCodeName(dto.getName());
        card.setState(CardState.ACTIVE);
        card.setImage(fileService.getById(dto.getImageId()));
        card = cardRepository.save(card);
        response.setData(cardMapper.mapToAdminDto(card));
        return response;
    }


    @Override
    public BotResponse<CardDtoForUser> buyCard(Long id) {
        BotResponse<CardDtoForUser> response = new BotResponse<>();
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty()) {
            response.addError(ResponseError.CARD_NOT_FOUND);
            return response;
        }
        Card card = optionalCard.get();
        Optional<CardInstance> optional = instanceRepository.findByUserAndCard(CurrentUserInfo.getCurrentUser(),card);
        if(optional.isPresent()) {
            response.addError(ResponseError.CARD_ALREADY_BOUGHT);
            return response;
        }
        Balance balance = balanceService.getCurrentBalance();
        if (balance == null || balance.getAmount() == null) {
            response.addError(ResponseError.BALANCE_ERROR_TRY_LATER);
            return response;
        }
        Double cardPrice = card.getCardLevelMap().getCardLevelMap().get(1).levelPrice;
        Boolean isBalanced = false;
        if (balance.getAmount() >= cardPrice) {
            try {
                isBalanced = balanceService.updateBalance(cardPrice, OperationType.BUY, balance);
                if (isBalanced) {
                    instanceService.setCardForUser(card);
                    User user = CurrentUserInfo.getCurrentUser();
                    Double newPower = card.getCardLevelMap().getCardLevelMap().get(1).power;
                    if (user.getSummaryPower() == null) {
                        user.setSummaryPower(newPower);
                    } else {
                        user.setSummaryPower(user.getSummaryPower() + newPower);
                    }
                    user = userRepository.save(user);
                } else {
                    response.addError(ResponseError.BALANCE_ERROR_TRY_LATER);
                }
            } catch (Exception ex) {
                //возврат средств в случае ошибки
                balanceService.updateBalance(cardPrice, OperationType.EARN, balance);
                response.addError(ResponseError.BALANCE_ERROR_TRY_LATER);
            }
        } else {
            response.addError(ResponseError.NOT_ENOUGH_MONEY);
        }
        response.setData(cardMapper.mapToUserDto(card));
        return response;

    }

    @Override
    public BotResponse<CardDtoForUser> upgradeCardToNextLevel(Long id) {
        BotResponse<CardDtoForUser> response = new BotResponse<>();
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isEmpty()) {
            response.addError(ResponseError.CARD_NOT_FOUND);
            return response;
        }
        Card card = optionalCard.get();
        Balance balance = balanceService.getCurrentBalance();
        if (balance == null || balance.getAmount() == null) {
            response.addError(ResponseError.BALANCE_ERROR_TRY_LATER);
            return response;
        }
        CardInstance instance = instanceService.getInstance(card);
        if (instance == null) {
            response.addError(ResponseError.CANT_UPGRADE_BUY_YET);
            return response;
        }

        Double cardPrice = card.getCardLevelMap().getCardLevelMap().get(instance.getLevel() + 1).levelPrice;
        Boolean isBalanced = false;
        if (balance.getAmount() >= cardPrice) {
            balance.setAmount(balance.getAmount() - cardPrice);
            try {
                isBalanced = balanceService.updateBalance(cardPrice, OperationType.BUY, balance);
                if (isBalanced) {
                    Double oldPower = card.getCardLevelMap().getCardLevelMap().get(instance.getLevel()).power;
                    Double newPower = card.getCardLevelMap().getCardLevelMap().get(instance.getLevel() + 1).power;
                    instanceService.updateInstance(instance, newPower);
                    User user = CurrentUserInfo.getCurrentUser();
                    if (user.getSummaryPower() == null) {
                        user.setSummaryPower(newPower);
                    } else {
                        user.setSummaryPower(user.getSummaryPower() + (newPower - oldPower));
                    }
                    user = userRepository.save(user);
                } else {
                    response.addError(ResponseError.BALANCE_ERROR_TRY_LATER);
                }
            } catch (Exception ex) {
                //возврат средств в случае ошибки
                balanceService.updateBalance(cardPrice, OperationType.EARN, balance);
                response.addError(ResponseError.BALANCE_ERROR_TRY_LATER);
            }
        } else {
            response.addError(ResponseError.NOT_ENOUGH_MONEY);
        }
        response.setData(cardMapper.mapToUserDto(card));
        return response;
    }


    @Override
    public BotResponse<List<CardDtoForUser>> getAllCards() {
        BotResponse<List<CardDtoForUser>> response = new BotResponse<>();
        List<Card> cards = cardRepository.findAll();
        response.setData(cardMapper.mapToUserDto(cards));
        return response;
    }

    @Override
    public BotResponse<List<CardDtoForAdmin>> getAllCardsForAdmin() {
        BotResponse<List<CardDtoForAdmin>> response = new BotResponse<>();
        List<Card> cards = cardRepository.findAll();
        response.setData(cardMapper.mapToAdminDto(cards));
        return response;
    }

    @Override
    public BotResponse<?> deleteCard(Long cardId) {
        BotResponse<List<CardDtoForAdmin>> response = new BotResponse<>();
        Optional<Card> optionalCard = cardRepository.findById(cardId);
        if (optionalCard.isEmpty()) {
            response.addError(ResponseError.CARD_NOT_FOUND);
            return response;
        }
        Card card = optionalCard.get();
        card.setState(CardState.DELETED);
        cardRepository.save(card);
        return response;
    }


    @PostConstruct
    public void init() {
        if (testingMode != null && testingMode) {
            List<Card> cards = cardRepository.findAll();
            if (CollectionUtils.isEmpty(cards)) {
                Card card = new Card();
                card.setIsStartCard(true);
                card.setMaxLevel(2);
                card.setState(CardState.ACTIVE);
                card.setImage(null);
                card.setCodeName("TEST_CARD_1");
                CardInfo cardInfo = new CardInfo();
                Map<Language, NameAndDescription> infoMap = new HashMap<>();
                infoMap.put(Language.RU, new NameAndDescription("Карта1", "Ну карточка, что сказать"));
                infoMap.put(Language.EN, new NameAndDescription("Card1", "Some default card.."));
                cardInfo.setLanguageNameAndDescriptionMap(infoMap);
                card.setCardInfo(cardInfo);
                CardLevelMap levelMap = new CardLevelMap();
                Map<Integer, CardParameters> paramMap = new HashMap<>();
                paramMap.put(1, new CardParameters(100.0, 0.0));
                paramMap.put(2, new CardParameters(200.0, 300.0));
                levelMap.setCardLevelMap(paramMap);
                card.setCardLevelMap(levelMap);
                cardRepository.save(card);



                Card card2 = new Card();
                card2.setIsStartCard(false);
                card2.setMaxLevel(2);
                card2.setState(CardState.ACTIVE);
                card2.setImage(null);
                card2.setCodeName("TEST_CARD_2");
                CardInfo cardInfo2 = new CardInfo();
                Map<Language, NameAndDescription> infoMap2 = new HashMap<>();
                infoMap2.put(Language.RU, new NameAndDescription("Карта2", "Ну карточка 2, что сказать"));
                infoMap2.put(Language.EN, new NameAndDescription("Card2", "Some default card 2.."));
                cardInfo2.setLanguageNameAndDescriptionMap(infoMap2);
                card2.setCardInfo(cardInfo2);
                CardLevelMap levelMap2 = new CardLevelMap();
                Map<Integer, CardParameters> paramMap2 = new HashMap<>();
                paramMap2.put(1, new CardParameters(1400.0, 100.0));
                paramMap2.put(2, new CardParameters(2200.0, 300.0));
                levelMap2.setCardLevelMap(paramMap2);
                card2.setCardLevelMap(levelMap2);
                cardRepository.save(card2);
            }
        }
    }
}
