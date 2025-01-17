package com.ece.bot.web.rest.admin;


import com.ece.bot.dto.karma.res.KarmasByCategory;
import com.ece.bot.dto.system.BotResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/karma")
public class KarmaAdminRest {

    @PostMapping(value = "/create")
    private BotResponse<KarmasByCategory> createKarma(){ //todo  body
        return null;
    }

    @DeleteMapping(value = "/delete")
    private BotResponse<?> deleteKarmaCard(@RequestParam(name = "karmaId") Long karmaId){
        return null;
    }

    @GetMapping(value = "/getAll")
    private BotResponse<List<KarmasByCategory>> getAllKarma(){
        return null;
    }
}
