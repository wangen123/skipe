package com.cn.bussinessserverr.controller;

import com.cn.bussinessserverr.model.Message;
import com.cn.bussinessserverr.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @RequestMapping(value = "/add/{commodityName}")
    public Message createCommodity(@PathVariable("commodityName") String commodityName){
        Message message = new Message("200","success");
        message = commodityService.createCommodity(commodityName,message);
        return message;
    }
}
