package com.ece.bot.web.rest.admin;


import com.ece.bot.dto.statistic.req.SearchStatistic;
import com.ece.bot.dto.statistic.res.StatisticDto;
import com.ece.bot.dto.system.BotResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/statistic")
public class StatisticRest {
    @PostMapping(value = "/search")
    public BotResponse<List<StatisticDto>> search(@RequestBody SearchStatistic dto){
        return null; //TODO
    }
}
