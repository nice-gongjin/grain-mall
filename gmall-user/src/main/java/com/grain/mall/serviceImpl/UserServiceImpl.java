package com.grain.mall.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.UmsMember;
import com.gj.services.UserService;
import com.grain.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UmsMember> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Object> getUsers() {
        return userMapper.getUsers();
    }

}
