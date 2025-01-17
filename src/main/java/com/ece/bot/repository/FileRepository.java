package com.ece.bot.repository;

import com.ece.bot.model.CardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<CardFile,Long> {
}
