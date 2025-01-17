package com.ece.bot.service.impl;

import com.ece.bot.common.EventType;
import com.ece.bot.common.UserState;
import com.ece.bot.dto.statistic.res.StatisticDto;
import com.ece.bot.helper.CurrentUserInfo;
import com.ece.bot.model.User;
import com.ece.bot.model.system.BotEvent;
import com.ece.bot.repository.UserRepository;
import com.ece.bot.repository.system.EventRepository;
import com.ece.bot.service.StatisticService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {
    @Value("${app.conf.stat.visit-detect.enabled}")
    private Boolean visitDetectEnabled;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Override
    public List<StatisticDto> searchStatistic() {
        return List.of();
    }

    @Override
    public void updateLastVisitTime() {
        if(visitDetectEnabled){
            //TODO
        }
    }

    @Override
    public LocalDateTime getLastVisit(String chatId) {
        return null; //TODO
    }

    @Override
    public void pushNewEvent(EventType type, String description,String objId) {
        if(!visitDetectEnabled && type.equals(EventType.VISIT)){
            return;
        }
        BotEvent event = new BotEvent();
        event.setIpAddress(CurrentUserInfo.getIp());
        event.setEventTime(LocalDateTime.now());
        if(CurrentUserInfo.getCurrentUserInfo() != null){
            Optional<User> optionalUser = userRepository.findByTgChatIdAndStateIn(
                    CurrentUserInfo.getCurrentUserInfo().getChatId(), Arrays.asList(UserState.values())
            );
            optionalUser.ifPresent(event::setUser);
        }
        event.setDescription(description);
        event.setObjId(objId);
        eventRepository.save(event);
    }
}
