package com.cn.skipedemo.service;

import com.cn.skipedemo.model.Commodity;
import com.cn.skipedemo.model.Message;
import com.cn.skipedemo.repo.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CommodityService {

    private static volatile AtomicInteger integer = new AtomicInteger(10000);

    @Autowired
    private CommodityRepository repository;

    public Commodity findByName(String name){
        return repository.findByName(name);
    }

    public void updateCommodity(Commodity commodity) {
        repository.save(commodity);
    }

    public Message createCommodity(String commodityName, Message message) {
        Commodity commodity = new Commodity();
        commodity.setId(integer.incrementAndGet());
        commodity.setCommodityName(commodityName);
        commodity.setNumber(10);
        commodity.setCommodityPrice(new BigDecimal(5000));
        repository.save(commodity);
        return message;
    }
}
