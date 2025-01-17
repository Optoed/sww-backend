package com.ece.bot.model;


import com.ece.bot.common.CardState;
import com.ece.bot.model.addittional.CardInfo;
import com.ece.bot.model.addittional.CardLevelMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card")
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_code_name")
    private String codeName;

    @Column(name = "card_info")
    @JdbcTypeCode(SqlTypes.JSON)
    private CardInfo cardInfo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private CardFile image;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_state")
    private CardState state;

    @Column(name = "card_level_map")
    @JdbcTypeCode(SqlTypes.JSON)
    private CardLevelMap cardLevelMap;

    @Column(name = "card_max_level")
    private Integer maxLevel;

    @Column(name = "is_start_card")
    private Boolean isStartCard;
}
