package com.ece.bot.service;

import com.ece.bot.common.OperationType;
import com.ece.bot.model.Balance;
import com.ece.bot.model.User;

public interface BalanceService {
    public void createBalanceForNewUser(User user);
    public Balance getCurrentBalance();
    public Boolean updateBalance(Double amount, OperationType type,Balance balance);
}
