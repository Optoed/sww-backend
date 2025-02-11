package com.ece.bot.repository;

import com.ece.bot.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByIsStartCard(Boolean isStart);
    Optional<Card> findByCodeName(String codeName);
}
