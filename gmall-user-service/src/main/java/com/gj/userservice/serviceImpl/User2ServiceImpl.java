package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.UmsMember;
import com.gj.services.UserService;
import com.gj.userservice.mapper.User2Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class User2ServiceImpl extends ServiceImpl<User2Mapper, UmsMember> implements UserService {

    @Autowired
    User2Mapper user2Mapper;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Object> getUsers() {
        return user2Mapper.getUsers();
    }

    @Override
    public UmsMember login(UmsMember umsMember) {
        try {
            // 从缓存中验证用户（ 缓存中用户的信息： K=user:password:login，V=UmsMember ）
            if (null != redisTemplate){
                // redis连接成功
                // 获取用户名和密码
                String password = umsMember.getPassword();
                String userId = umsMember.getId();
                String userInfo = redisTemplate.opsForValue().get("user:" + userId + ":" + password + ":login");
                if (StringUtils.isNotBlank(userInfo)) {
                    // 若缓存中有用户的信息
                    UmsMember memberInfo = JSON.parseObject(userInfo, UmsMember.class);
                    System.out.println("****** memberInfo = " + memberInfo);
                    return memberInfo;
                }
            }
            // redis连接失败或者缓存中没有用户的信息，调用数据库
            List<UmsMember> umsMembers = user2Mapper.selectList(new EntityWrapper<UmsMember>());
            if (null != umsMembers) {
                // 若数据库有该用户则更新到缓存中
                String password = umsMembers.get(0).getPassword();
                String userId = umsMembers.get(0).getId();
                UmsMember memberInfo = umsMembers.get(0);
                String V = JSON.toJSONString(memberInfo);
                redisTemplate.opsForValue().set("user:"+userId+":"+password+":login",V,1, TimeUnit.DAYS);
                System.out.println("****** memberInfo222 = " + memberInfo);
                return memberInfo;
            }
            System.out.println("****** redisTemplate = " + redisTemplate);
        }finally {
            System.out.println("****** finally ");
        }
        return null;
    }

    @Override
    public Boolean addRedis(String token,String userId) {
        try {
            if (StringUtils.isBlank(token)) return false;
            redisTemplate.opsForValue().set("user:" + userId + ":token",token,1,TimeUnit.DAYS);
            return true;
        }catch (Exception e){
            System.out.println("****** token添加到缓存出错！ UserServiceImpl的69行。。。");
        }
        return false;
    }

}
