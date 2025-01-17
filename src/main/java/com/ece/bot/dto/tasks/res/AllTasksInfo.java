package com.ece.bot.dto.tasks.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllTasksInfo {
    private List<TasksByCategory> tasks;
    private List<TaskDto> promoTasks;
    private Boolean canClaimPromoTasks;
}
