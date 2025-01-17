package com.ece.bot.repository;

import com.ece.bot.model.Karma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KarmaRepository extends JpaRepository<Karma, Long> {
}
