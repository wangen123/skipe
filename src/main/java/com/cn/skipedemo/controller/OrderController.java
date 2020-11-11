package com.cn.skipedemo.controller;

import com.cn.skipedemo.model.Message;
import com.cn.skipedemo.model.User;
import com.cn.skipedemo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /***
     * 订单服务
     * @param username
     * @param commodityName
     * @param number
     * @return
     */
    @RequestMapping(value = "/order/{username}/{commodityName}/{number}")
    public Message order(@PathVariable("username") String username,
                         @PathVariable("commodityName") String commodityName,
                         @PathVariable("number")String number){
        //首先判断用户是否登录，假定用户登录是存在redis中
        Message message = new Message("200","success");
        message = orderService.order(username,commodityName,number,message);
        return message;
    }



}
