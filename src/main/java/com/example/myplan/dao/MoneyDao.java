package com.example.myplan.dao;

import com.example.myplan.pojo.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface MoneyDao extends JpaRepository<Money,Long>, CrudRepository<Money,Long> {
}
