package com.ece.bot.repository.system;

import com.ece.bot.model.system.BotEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<BotEvent, Long> {
}
