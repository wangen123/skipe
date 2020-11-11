package com.cn.skipedemo.controller;

import com.cn.skipedemo.model.Message;
import com.cn.skipedemo.service.UserOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserOptionController {

    @Autowired
    private UserOptionService service;


    @RequestMapping(value = "/create/{username}")
    public Message createUser(@PathVariable("username") String name) {
        Message message = new Message();
        service.createUser(name);
        return message;
    }

    @RequestMapping(value = "/delete/{username}",method = RequestMethod.DELETE)
    public Message deleteUser(@PathVariable("useranme") String name){
        return service.deleteUser(name);


    }

}
