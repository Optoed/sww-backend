package com.ece.bot.dto.statistic.req;

import com.ece.bot.common.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchStatistic {
    private LocalDateTime minDate;
    private LocalDateTime maxDate;
    private Integer pageNum;
    private Integer pageSize;
    private EventType type;
}
