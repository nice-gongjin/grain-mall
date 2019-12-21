package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.UmsMember;
import com.gj.services.UserService;
import com.gj.userservice.mapper.User2Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class User2ServiceImpl extends ServiceImpl<User2Mapper, UmsMember> implements UserService {

    @Autowired
    User2Mapper user2Mapper;

    @Override
    public List<Object> getUsers() {
        return user2Mapper.getUsers();
    }

}
