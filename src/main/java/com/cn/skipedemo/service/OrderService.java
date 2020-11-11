package com.cn.skipedemo.service;

import com.cn.skipedemo.model.Commodity;
import com.cn.skipedemo.model.Message;
import com.cn.skipedemo.model.User;
import com.cn.skipedemo.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CommodityService commodityService;

    public Message order(String username, String commodityName, String number, Message message) {
        //首先校验用户是否登录：
        User user = (User) redisUtils.get(username);
        if(Integer.valueOf(number) > 1){
            message.setCode("0005");
            message.setMessage("the number is more than one");
            return message;
        }
        //这一步可以进行优化，不直接查库
        Commodity commodity = commodityService.findByName(commodityName);
        if(commodity.getNumber() == 0){
            message.setCode("0006");
            message.setMessage("the commodity is all saled");
            return message;
        }
        //这一步开始进行下单减库存
        commodity.setNumber(commodity.getNumber() - 1);
        commodityService.updateCommodity(commodity);

        return message;
    }
}
