package com.ece.bot.repository;

import com.ece.bot.model.Card;
import com.ece.bot.model.CardInstance;
import com.ece.bot.model.User;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstanceRepository extends JpaRepository<CardInstance, Long> {
    List<CardInstance> findByUserAndCardIn(User user, List<Card> cards);
    Optional<CardInstance> findByUserAndCard(User user, Card card);
}
