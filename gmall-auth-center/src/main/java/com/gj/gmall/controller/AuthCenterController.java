package com.gj.gmall.controller;

import com.gj.entitys.UmsMember;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthCenterController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(UmsMember umsMember){
        // 调用用户服务验证用户的信息

        return "tooken";
    }

    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public String index(String ReturnUrl, ModelMap modelMap){
        // 调用用户服务验证用户的信息

        modelMap.put("ReturnUrl", ReturnUrl);

        return "index";
    }

}
