package com.example.myplan.service;

import com.example.myplan.dao.MoneyDao;
import com.example.myplan.pojo.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MoneyService {

    @Autowired
    private MoneyDao moneyDao;

    public Optional<Money> getMoneyById(Long id){
        Optional<Money> byId = moneyDao.findById(id);
        return byId;
    }

    public Money addMoney(Money money){
        return moneyDao.save(money);
    }
}
