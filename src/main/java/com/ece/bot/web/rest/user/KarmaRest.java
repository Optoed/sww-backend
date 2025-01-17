package com.ece.bot.web.rest.user;

import com.ece.bot.dto.karma.res.KarmasByCategory;
import com.ece.bot.dto.system.BotResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/karma")
public class KarmaRest {
    @PostMapping(value = "/getAll")
    public BotResponse<List<KarmasByCategory>> getAllKarmas() {
        return null;
    }

    @PostMapping(value = "/donate")
    public BotResponse<KarmasByCategory> donate(@RequestParam(name = "karmaId") Long karmaId,
                                                @RequestParam(name = "donate") Double donate){
        return null;
    }
}
