package com.ece.bot.dto.statistic.res;

import com.ece.bot.common.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticDto {
    private LocalDateTime eventTime;
    private EventType type;
    private String description;
}
