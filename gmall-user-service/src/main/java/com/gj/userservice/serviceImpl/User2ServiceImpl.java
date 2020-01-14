package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.UmsMember;
import com.gj.services.User2Service;
import com.gj.userservice.mapper.User2Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class User2ServiceImpl extends ServiceImpl<User2Mapper, UmsMember> implements User2Service {

    @Autowired
    User2Mapper user2Mapper;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

}
