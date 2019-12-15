package com.grain.mall.controller;

import com.alibaba.fastjson.JSON;
import com.gj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/list")
    public List<Object> get(){
        System.out.println("****** " + JSON.toJSONString(userService.getUsers()));
        return userService.getUsers();
    }

}
