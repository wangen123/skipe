package com.cn.skipedemo.service;

import com.cn.skipedemo.model.Commodity;
import com.cn.skipedemo.repo.CommodityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityService {

    @Autowired
    private CommodityRepository repository;

    public Commodity findByName(String name){
        return repository.findByName(name);
    }

    public void updateCommodity(Commodity commodity) {
        repository.save(commodity);
    }
}
