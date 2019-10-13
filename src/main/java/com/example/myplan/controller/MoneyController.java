package com.example.myplan.controller;

import com.example.myplan.pojo.Money;
import com.example.myplan.service.MoneyService;
import com.example.myplan.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
public class MoneyController {

    @Autowired
    private MoneyService moneyService;


    @GetMapping("/money/getById")
    public R getMoneyById(Long id){
        Optional<Money> moneyById = moneyService.getMoneyById(id);
        return R.isOk().data(moneyById);
    }

    @GetMapping("/money/add")
    public R addMoney(Double imoney,Integer itype){
        Money money = new Money().setItype(itype).setItime(new Date()).setImoney(imoney);
        return R.isOk().data(moneyService.addMoney(money));
    }
}
