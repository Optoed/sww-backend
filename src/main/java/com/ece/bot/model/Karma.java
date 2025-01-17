package com.ece.bot.model;


import com.ece.bot.common.CardState;
import com.ece.bot.common.KarmaCategory;
import com.ece.bot.common.KarmaState;
import com.ece.bot.model.addittional.CardInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "karma")
@Entity
public class Karma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_code_name")
    private String codeName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private CardFile image;

    @Enumerated(EnumType.STRING)
    @Column(name = "karma_state")
    private KarmaState state;

    @Column(name = "karma_goal")
    private Double goal;

    @Column(name = "current_summ")
    private Double currentSumm;

    @Column(name = "karma_info")
    @JdbcTypeCode(SqlTypes.JSON)
    private CardInfo karmaInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "karma_category")
    private KarmaCategory category;
}
