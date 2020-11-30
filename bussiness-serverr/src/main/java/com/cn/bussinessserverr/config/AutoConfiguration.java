package com.cn.bussinessserverr.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfiguration {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;
    @Bean
    public Redisson redisson(){
        Config config = new Config();
        //单机模式
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        //集群模式
        //config.useClusterServers().addNodeAddress("","","");
        return (Redisson) Redisson.create(config);
    }


}
