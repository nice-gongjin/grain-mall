package com.grain.mall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.gj.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    UserService userService;

    @RequestMapping("/list")
    public List<Object> get(){
        System.out.println("访问开始!");
        System.out.println("****** " + JSON.toJSONString(userService.getUsers()));
        System.out.println("访问结束！");
        return userService.getUsers();
    }

}
