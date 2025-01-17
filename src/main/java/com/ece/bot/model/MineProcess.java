package com.ece.bot.model;


import com.ece.bot.common.ProcessState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mine_process")
@Entity
public class MineProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "process_state")
    private ProcessState state;

    @Column(name = "process_power")
    private Double processPower;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "calculated_stop_time")
    private LocalDateTime calculatedStopTime;

    @Column(name = "fact_stop_time")
    private LocalDateTime factStopTime;

    @Column(name = "claim_time")
    private LocalDateTime claimTime;
}
