package com.gj.gmall.util.sql;

import javax.annotation.PostConstruct;

//@Component
public class MapperUtil {

//    @Autowired
//    private UserMapper userMapper;

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
