package com.ece.bot.service.impl;

import com.ece.bot.common.OperationType;
import com.ece.bot.helper.CurrentUserInfo;
import com.ece.bot.model.Balance;
import com.ece.bot.model.User;
import com.ece.bot.repository.BalanceRepository;
import com.ece.bot.service.BalanceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    @Value("${app.conf.register.start-balance}")
    private Double startBalance;

    @Autowired
    private BalanceRepository balanceRepository;
    @Override
    public void createBalanceForNewUser(User user) {
        Balance balance = new Balance();
        balance.setAmount(startBalance);
        balance.setUser(user);
        balanceRepository.save(balance);
    }

    @Override
    public Balance getCurrentBalance() {
        Optional<Balance> optionalBalance = balanceRepository
                .findByUser(CurrentUserInfo.getCurrentUser());
        return optionalBalance.orElse(null);
    }

    @Override
    public Boolean updateBalance(Double amount, OperationType type, Balance balance) {
        Boolean success = true;
        if(type.equals(OperationType.BUY)){
            if(balance.getAmount() >= amount){
                balance.setAmount(balance.getAmount() - amount);
            }else{
                return false;
            }
        }else if (type.equals(OperationType.EARN)){
            balance.setAmount(balance.getAmount() + amount);
        }
        balance = balanceRepository.save(balance);
        return true;
    }
}
