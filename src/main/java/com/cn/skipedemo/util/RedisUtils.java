package com.cn.skipedemo.util;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Redisson redisson;


    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public String get(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void expire(String key, Long timeout){
        redisTemplate.expire(key,timeout,TimeUnit.SECONDS);
    }
}

/****
 * 通过使用ression进行使用redis分布式锁
 */
    /*public void setLockKey{
        String lockKey = "redis_lock_key";
        RLock rlock = redisson.getLock(lockKey);
        rlock.lock(30,TimeUnit.SECONDS);
        try{
            //加锁的业务


        }finally {
            rlock.unlock();
        }

    }*/