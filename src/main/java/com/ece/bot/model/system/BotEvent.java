package com.ece.bot.model.system;


import com.ece.bot.common.EventType;
import com.ece.bot.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bot_event")
@Entity
public class BotEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_time")
    private LocalDateTime eventTime;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Column(name = "objId")
    private String objId;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ip_adress")
    private String ipAddress;
}
