package com.cn.skipedemo.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfiguration {


    @Bean
    public Redisson redisson(){
        Config config = new Config();
        //单机模式
        config.useSingleServer().setAddress("redis://192.168.136.128:6379");
        //集群模式
        //config.useClusterServers().addNodeAddress("","","");
        return (Redisson) Redisson.create(config);
    }


}
