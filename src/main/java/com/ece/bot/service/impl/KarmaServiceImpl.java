package com.ece.bot.service.impl;

import com.ece.bot.dto.karma.req.CreateKarmaDto;
import com.ece.bot.dto.karma.res.KarmaCardDto;
import com.ece.bot.dto.karma.res.KarmasByCategory;
import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.service.KarmaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KarmaServiceImpl implements KarmaService {
    @Override
    public BotResponse<List<KarmasByCategory>> getAllKarmaCards() {
        return null;
    }

    @Override
    public BotResponse<KarmaCardDto> donateToKarmaCard(Long karmaId, Double summ) {
        return null;
    }

    @Override
    public BotResponse<?> deleteKarmaCard(Long karmaId) {
        return null;
    }

    @Override
    public BotResponse<KarmaCardDto> createKarmaCard(CreateKarmaDto createKarmaDto) {
        return null;
    }
}
