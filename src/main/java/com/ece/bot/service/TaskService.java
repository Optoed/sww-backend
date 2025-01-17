package com.ece.bot.service;

import com.ece.bot.dto.system.BotResponse;
import com.ece.bot.dto.tasks.req.CreateTaskDto;
import com.ece.bot.dto.tasks.res.AllTasksInfo;
import com.ece.bot.dto.tasks.res.TaskDto;

public interface TaskService {
    public BotResponse<AllTasksInfo> getAllTasks();
    public BotResponse<TaskDto> createTask(CreateTaskDto taskDto);
    public BotResponse<?> deleteTask(Long taskId);
    public BotResponse<TaskDto> completeTask(Long taskId);
    public BotResponse<?> claimPromoTasks();
}
