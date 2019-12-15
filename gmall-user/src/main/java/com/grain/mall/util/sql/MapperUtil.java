package com.grain.mall.util.sql;

import com.grain.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class MapperUtil {
    @Autowired
    private UserMapper userMapper;

    private static MapperUtil mapperUtil;

    @PostConstruct
    public void init() {
        mapperUtil = this;
    }

    public static MapperUtil getInstance() {
        return mapperUtil;
    }

    public UserMapper getUserMapper() {
        return this.userMapper;
    }

}
