package com.ece.bot.service;

import com.ece.bot.dto.karma.req.CreateKarmaDto;
import com.ece.bot.dto.karma.res.KarmaCardDto;
import com.ece.bot.dto.karma.res.KarmasByCategory;
import com.ece.bot.dto.system.BotResponse;

import java.util.List;

public interface KarmaService {
    public BotResponse<List<KarmasByCategory>> getAllKarmaCards(); //for user and for admin
    public BotResponse<KarmaCardDto> donateToKarmaCard(Long karmaId, Double summ);
    public BotResponse<?> deleteKarmaCard(Long karmaId);
    public BotResponse<KarmaCardDto> createKarmaCard(CreateKarmaDto createKarmaDto);
}
