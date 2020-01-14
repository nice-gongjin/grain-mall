package com.grain.mall.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.UmsMember;
import com.gj.services.UserService;
import com.grain.mall.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UmsMember> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate<String, String> redisTemplate;
    DefaultRedisScript<Long> script;

    @Override
    public List<Object> getUsers() {
        return userMapper.getUsers();
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
                    System.out.println("****** memberInfo333 = " + memberInfo);
                    return memberInfo;
                }
            }
            // redis连接失败或者缓存中没有用户的信息，调用数据库
            List<UmsMember> umsMembers = userMapper.selectList(new EntityWrapper<UmsMember>());
            if (null != umsMembers) {
                // 若数据库有该用户则更新到缓存中
                String password = umsMembers.get(0).getPassword();
                String userId = umsMembers.get(0).getId();
                UmsMember memberInfo = umsMembers.get(0);
                String V = JSON.toJSONString(memberInfo);
                redisTemplate.opsForValue().set("user:"+userId+":"+password+":login",V,1, TimeUnit.DAYS);
                System.out.println("****** memberInfo444 = " + memberInfo);
                return memberInfo;
            }
            System.out.println("****** redisTemplate = " + redisTemplate);
        }finally {
            System.out.println("****** 错误！");
        }
        return null;
    }

    @Override
    public Boolean addRedis(String token, String userId) {
        try {
            if (StringUtils.isBlank(token) || null == redisTemplate) return false;
            redisTemplate.opsForValue().set("user:" + userId + ":token",token,1,TimeUnit.DAYS);
            return true;
        }catch (Exception e){
            System.out.println("****** token添加到缓存出错！ UserServiceImpl的69行。。。");
        }
        return false;
    }

    @Override
    public Boolean cheackTradeCode(String userId, String tradeCode) {
        try {
            ValueOperations<String, String> redis = redisTemplate.opsForValue();
            // lua脚本，对比防重删令牌
            String lua = null;
            if (StringUtils.isNotBlank(tradeCode)) {
                lua = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                script = new DefaultRedisScript<Long>();
                script.setResultType(Long.class);
                script.setScriptText(lua);
                List<String> keys = new ArrayList<>();
                keys.add("trade:" + userId + ":code");
                Long execute = redisTemplate.execute(script, keys, tradeCode);
                if (null != execute && execute != 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }catch (Exception e) {
            System.out.println("****** e = " + e.getMessage());
        }
        return false;
    }

}
