package com.ece.bot.model;


import com.ece.bot.common.Language;
import com.ece.bot.common.UserState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tg_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tg_chat_id")
    private String tgChatId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "welcome_date")
    private LocalDateTime welcomeDate;

    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState state;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CardInstance> cardInstances;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Balance balance;

    @Column(name = "user_language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "summary_power")
    private Double summaryPower;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> referals;

    @Column(name = "referal_code")
    private String referalCode;
}
