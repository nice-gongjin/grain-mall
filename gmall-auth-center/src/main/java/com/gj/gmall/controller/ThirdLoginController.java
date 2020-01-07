package com.gj.gmall.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/third")
public class ThirdLoginController {

    // 第三方登录，按照第三方的文档进行开发
    @RequestMapping(value = "/thirdlogin", method = RequestMethod.POST)
    public String thirdLogin(String code, HttpServletRequest request){
        // 用户在客户端授权登录
        // 请求第三方授权服务器获取授权码
        // 用授权码请求认证服务器换取access_token
        // 用access_token请求资源服务器换取用户数据
        // 将获取到的用户数据保存到数据库中并设置sourceType
        // 用JWT的工具类和加密工具类生成token
        String token = null;
        // 将加密后的token保存到缓存中
        // 生成JWT的token并将该token重定向到首页

        return "redirect:http://search.gmall.com:10022/index?token=" + token;
    }

    // 用户在客户端授权登录
    public String userAuth(){
        return null;
    }

    // 请求第三方授权服务器获取授权码
    public String authCode(){
        return null;
    }

    // 用授权码请求认证服务器换取access_token
    public String accessToken(){
        return null;
    }

    // 用access_token请求资源服务器换取用户数据
    public String userInfo(){
        return null;
    }

    // 第三方登录，按照第三方的文档进行开发
    @RequestMapping(value = "/thirdlogout", method = RequestMethod.POST)
    public String thirdLogout(String code, HttpServletRequest request){
        return null;
    }

}
