package com.cn.bussinessserverr.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.cn.bussinessserverr.model.Commodity;
import com.cn.bussinessserverr.model.Message;
import com.cn.bussinessserverr.model.Order;
import com.cn.bussinessserverr.model.User;
import com.cn.bussinessserverr.repo.OrderRepository;
import com.cn.bussinessserverr.util.RedisUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional
public class OrderService {
    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final static String lockKey = "redis_lock_key";
    private final static AtomicInteger integer = new AtomicInteger(0);
    private static ReentrantLock lock = new ReentrantLock();

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private Redisson redisson;

    @Transactional
    public Message order(String username, String commodityName, String number, Message message) throws InterruptedException {
        Date date = new Date();
        //针对订单这个操作需要做幂等校验
        Order order = new Order();

        //首先校验用户是否登录：
        //校验这部分可以考虑做成职责链来进行处理
        User user = (User) redisUtils.get(username);
        if(Integer.valueOf(number) > 1){
            message.setCode("0005");
            message.setMessage("the number is more than one");
            return message;
        }
        lock.lock();
        try{
            Commodity commodity = commodityService.findByName(commodityName);
            if(commodity.getNumber() == 0){
                message.setCode("0006");
                message.setMessage("the commodity is all saled");
                return message;
            }
            //这一步可以进行优化，不直接查库
            //这一步开始进行下单减库存
            //下单的过程应该需要加锁这里使用redis 分布式锁
            commodity.setNumber(commodity.getNumber() - 1);
            commodityService.updateCommodity(commodity);
            //记录订单信息
            order.setCommodityId(commodity.getId());
            order.setCost(commodity.getCommodityPrice());
            order.setCreateTime(date);
            order.setUserId(123123);
            order.setId(integer.incrementAndGet());
            order.setSerialNumber(String.valueOf(Math.random()));
            orderRepository.save(order);
            //将生成的订单信息发送到订单服务
            String messageInfo = JSONUtils.toJSONString(order);



        } finally {
            lock.unlock();
        }
        return message;
    }
}
