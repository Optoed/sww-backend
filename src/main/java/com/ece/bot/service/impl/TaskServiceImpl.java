package com.ece.bot.service.impl;

import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.tasks.req.CreateTaskDto;
import com.ece.bot.dto.tasks.res.AllTasksInfo;
import com.ece.bot.dto.tasks.res.TaskDto;
import com.ece.bot.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public BotResponse<AllTasksInfo> getAllTasks() {
        return null;
    }

    @Override
    public BotResponse<TaskDto> createTask(CreateTaskDto taskDto) {
        return null;
    }

    @Override
    public BotResponse<?> deleteTask(Long taskId) {
        return null;
    }

    @Override
    public BotResponse<TaskDto> completeTask(Long taskId) {
        return null;
    }

    @Override
    public BotResponse<?> claimPromoTasks() {
        return null;
    }
}
