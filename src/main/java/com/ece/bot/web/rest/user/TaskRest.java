package com.ece.bot.web.rest.user;


import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.tasks.res.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskRest {

    @PostMapping(value = "/getAll")
    public BotResponse<List<TaskDto>> getAllCards() {
        return null;
    }

    @PostMapping(value = "/complete")
    public BotResponse<?> completeTask(@RequestParam(name = "taskId") Long taskId){
        return null;
    }

    @PostMapping(value = "/claimPromo")
    public BotResponse<?> claimPromoTasks(){
        return null;
    }
}
