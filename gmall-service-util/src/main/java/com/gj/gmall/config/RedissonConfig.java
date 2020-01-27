package com.gj.gmall.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() throws Exception{
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.43.254:6379").setConnectTimeout(30000).setTimeout(30000);
        redisson = Redisson.create(config);
        //System.out.println("初始化RedissonClient: " + redisson.getConfig().toJSON().toString());

        return redisson;
    }

}
