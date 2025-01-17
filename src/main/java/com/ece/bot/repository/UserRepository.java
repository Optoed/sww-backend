package com.ece.bot.repository;

import com.ece.bot.common.UserState;
import com.ece.bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTgChatIdAndStateIn(String tgChatId, List<UserState> states);
    Optional<User> findByReferalCode(String referalCode);
}
