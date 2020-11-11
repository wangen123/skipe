package com.cn.skipedemo.service;

import com.cn.skipedemo.model.Message;
import com.cn.skipedemo.model.User;
import com.cn.skipedemo.repo.UserRepo;
import com.cn.skipedemo.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class UserOptionService {
    private final static Logger log = LoggerFactory.getLogger(UserOptionService.class);

    private volatile static AtomicInteger atomicInteger = new AtomicInteger(10000);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RedisUtils redisUtils;

    @Transactional
    public Message createUser(String name) {
        for(int i=0;i<1000;i++){
            User user = new User(name);
            user.setId(atomicInteger.incrementAndGet());
            user.setAddress("china");
            user.setEmail("123456@163.com");
            user.setPhone("131215" + atomicInteger);
            user.setIdCard("41038119925815156156156");
            redisUtils.set(name,user);
            userRepo.save(user);
        }
        log.info("success save user");
        return new Message("200","success");
    }

    /**
     * 这里只是为了随便做个删除  所以用姓名为参数
     * 实际应用改idcard来保证身份为唯一性
     * @param name
     * @return
     */
    public Message deleteUser(String name){
        User user = (User) redisUtils.get(name);
        //需要保证redis的数据和数据库的数据一致
        if(user == null){
            user = userRepo.findByName(name);
        }
        userRepo.delete(user);
        log.info("success delete user");
        return new Message("200","success");
    }
}
