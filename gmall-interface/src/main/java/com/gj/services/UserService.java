package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.UmsMember;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService extends IService<UmsMember> {

    List<Object> getUsers();

    UmsMember login(UmsMember umsMember);

    Boolean addRedis(String token,String userId);

    Boolean cheackTradeCode(String userId, String tradeCode);

    boolean logOut(HttpServletRequest request, HttpServletResponse response);

}
