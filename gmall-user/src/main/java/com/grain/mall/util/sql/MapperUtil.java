package com.grain.mall.util.sql;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

//@Component
public class MapperUtil {

    private static MapperUtil mapperUtil;

    @PostConstruct
    public void init() {
        mapperUtil = this;
    }

    public static MapperUtil getInstance() {
        return mapperUtil;
    }

//    public UserMapper getUserMapper() {
//        return this.userMapper;
//    }

}
