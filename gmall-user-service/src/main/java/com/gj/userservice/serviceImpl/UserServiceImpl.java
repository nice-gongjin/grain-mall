package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.UmsMember;
import com.gj.services.UserService;
import com.gj.userservice.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UmsMember> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Object> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public UmsMember login(UmsMember umsMember) {
        try {
            // 从缓存中验证用户（ 缓存中用户的信息： K=user:userId:password:login，V=UmsMember ）
            if (null != redisTemplate){
                // redis连接成功
                // 获取用户名和密码
                String userId = umsMember.getId();
                String password = umsMember.getPassword();
                String userInfo = redisTemplate.opsForValue().get("user:" + userId + ":" + password + ":login");
                if (StringUtils.isNotBlank(userInfo)) {
                    // 若缓存中有用户的信息
                    UmsMember memberInfo = JSON.parseObject(userInfo, UmsMember.class);
                    System.out.println("****** memberInfo333 = " + memberInfo);

                    return memberInfo;
                }
            }
            // redis连接失败或者缓存中没有用户的信息，调用数据库
            List<UmsMember> umsMembers = userMapper.selectList(new EntityWrapper<UmsMember>()
                    .eq("username", umsMember.getUsername()).eq("password", umsMember.getPassword())
            );
            System.out.println("****** umsMembers: " + umsMembers);
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
            System.out.println("****** 执行完成！");
        }
        return null;
    }

    @Override
    public Boolean addRedis(String token, String userId) {
        try {
            if (StringUtils.isNotBlank(token) && null != redisTemplate) {
                redisTemplate.opsForValue().set("user:" + userId + ":token", token, 1, TimeUnit.DAYS);
                return true;
            }
        }catch (Exception e){
            System.out.println("****** token添加到缓存出错！ UserServiceImpl的69行。。。");
        }
        return false;
    }

    @Override
    public Boolean cheackTradeCode(String userId, String tradeCode) {
        try {
            // lua脚本，对比防重删令牌
            String lua = null;
            if (StringUtils.isNotBlank(tradeCode)) {
                DefaultRedisScript<Long> script = new DefaultRedisScript<>();
                lua = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                script.setResultType(Long.class);
                script.setScriptText(lua);
                List<String> keys = new ArrayList<>();
                keys.add("trade:" + userId + ":code");
                if (null == redisTemplate) return false;
                Long execute = redisTemplate.execute(script, keys, tradeCode);
                if (null != execute && execute != 0) {
                    return true;
                }
            }
        }catch (Exception e) {
            System.out.println("****** e = " + e.getMessage());
        }
        return false;
    }

}
