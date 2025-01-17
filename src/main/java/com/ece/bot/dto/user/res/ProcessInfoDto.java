package com.ece.bot.dto.user.res;


import com.ece.bot.common.ProcessState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInfoDto {
    private Double miningResult;
    private Double speed;
    private ProcessState state;
    private Integer remainingSeconds;
    private Integer totalProcessSeconds;
}
