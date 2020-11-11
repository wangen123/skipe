package com.cn.skipedemo.service;

import com.cn.skipedemo.model.Commodity;
import com.cn.skipedemo.model.Message;
import com.cn.skipedemo.model.User;
import com.cn.skipedemo.util.RedisUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class OrderService {

    private final static String lockKey = "redis_lock_key";

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private Redisson redisson;

    @Transactional
    public Message order(String username, String commodityName, String number, Message message) {
        //首先校验用户是否登录：
        //校验这部分可以考虑做成职责链来进行处理
        User user = (User) redisUtils.get(username);
        if(Integer.valueOf(number) > 1){
            message.setCode("0005");
            message.setMessage("the number is more than one");
            return message;
        }
        //这一步可以进行优化，不直接查库

        RLock rlock = redisson.getLock(lockKey);
        rlock.lock(5, TimeUnit.SECONDS);
        try{
            Commodity commodity = commodityService.findByName(commodityName);
            if(commodity.getNumber() == 0){
                message.setCode("0006");
                message.setMessage("the commodity is all saled");
                return message;
            }
            //这一步开始进行下单减库存
            //下单的过程应该需要加锁这里使用redis 分布式锁
            commodity.setNumber(commodity.getNumber() - 1);
            commodityService.updateCommodity(commodity);
        }finally {
            rlock.unlock();
        }
        return message;
    }
}
