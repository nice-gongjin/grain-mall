package com.gj.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.UmsMember;
import com.gj.manage.mapper.LoginMapper;
import com.gj.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, UmsMember> implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public UmsMember loginCheack(String loginName, String passwd) {
        UmsMember result = null;
        List<UmsMember> umsMemberList = loginMapper.selectList(
                new EntityWrapper<UmsMember>().eq("username", loginName).eq("password", passwd)
        );
        if (null != umsMemberList && umsMemberList.size() > 0) {
            result = umsMemberList.get(0);
        }

        return result;
    }

}
