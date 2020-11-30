package com.cn.bussinessserverr.controller;

import com.cn.bussinessserverr.model.Message;
import com.cn.bussinessserverr.service.UserOptionService;
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
        Message message = new Message("200","success");
        service.createUser(name);
        return message;
    }

    @RequestMapping(value = "/delete/{username}",method = RequestMethod.DELETE)
    public Message deleteUser(@PathVariable("useranme") String name){
        return service.deleteUser(name);


    }

}
