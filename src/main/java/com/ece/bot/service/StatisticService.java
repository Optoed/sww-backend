package com.ece.bot.service;

import com.ece.bot.common.EventType;
import com.ece.bot.dto.statistic.res.StatisticDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {
    public List<StatisticDto> searchStatistic();
    public void updateLastVisitTime();
    public LocalDateTime getLastVisit(String chatId);
    public void pushNewEvent(EventType type, String description, String objId);
}
