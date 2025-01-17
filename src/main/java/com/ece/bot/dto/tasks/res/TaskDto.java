package com.ece.bot.dto.tasks.res;


import com.ece.bot.common.TaskCategory;
import com.ece.bot.common.TaskState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private Long money;
    private String title;
    private String imagePath;
    private String description;
    private TaskState state;
    private String url;
    private Boolean isPromo;
    private TaskCategory taskCategory;
}
