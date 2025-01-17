package com.ece.bot.dto.tasks.res;

import com.ece.bot.common.TaskCategory;
import com.ece.bot.dto.karma.res.KarmaCardDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TasksByCategory {
    private TaskCategory category;
    private Integer categoryId;
    private List<TaskDto> tasks;
}
