package com.gj.gmall.controller;

import com.alibaba.fastjson.JSON;
import com.gj.entitys.UmsMember;
import com.gj.gmall.utils.JwtUtil;
import com.gj.gmall.utils.MD5Util;
import com.gj.gmall.webContents.WebContents;
import com.gj.services.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthCenterController {

    @Autowired
    UserService userService;

    // 验证token
    // 如果这里是从连接器AuthorityFilter类中发送过来的请求，则IP是连接器服务器的IP，故要接收传过了的currentIP
    @RequestMapping(value = "/verify",method = RequestMethod.GET)
    public String verify(@PathVariable("token") String token, @PathVariable("currentIP") String IP){
        // 通过token验证用户身份
        // 从请求或者代理中获取用户的请求IP，当做JWT加密的盐值的一部分
        // （getRemoteAddr()：表示直接从浏览器的请求中获取用户IP，getHeader("x-forwarded-for")：表示从代理服务中获取用户IP）
        Map<String, String> successMap = null;
        if (StringUtils.isNotBlank(token)){
            String md5Key = WebContents.md5Key;
            Map<String, Object> decode = JwtUtil.decode(token,md5Key,IP + "FA19C450FE8129143D920F447D321659");
            if (null != decode){
                successMap = new HashMap<>();
                successMap.put("status", "success");
                successMap.put("userId", "memberId");
                successMap.put("nickname", "nickname");
            }

            return JSON.toJSONString(successMap);
        }
        return null;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(UmsMember umsMember, HttpServletRequest request){
        String token = "";
        // 调用用户服务验证用户的信息
        UmsMember userInfo = userService.login(umsMember);
        if (userInfo != null){
            // 登录成功
            // 从请求或者代理中获取用户的请求IP，当做JWT加密的盐值的一部分
            // （getRemoteAddr()：表示直接从浏览器的请求中获取用户IP，getHeader("x-forwarded-for")：表示从代理服务中获取用户IP）
            String realIP = null;
            String xff = request.getHeader("x-forwarded-for");
            System.out.println("****** xff = " + xff);
            if (StringUtils.isNotBlank(xff)) {
                realIP = xff;
                int index = xff.indexOf(",");
                if (-1 != index) {
                    xff = xff.substring(0,index);
                }
                System.out.println("****** xff222 = " + xff);
//                realIP = xff.trim();
                System.out.println("****** realIP = " + realIP);
            }else {
                realIP = request.getRemoteAddr();
            }
            if (StringUtils.isBlank(realIP)) {
                System.out.println("****** 获取用户请求IP失败！ AuthCenterController类中第49行。。。");
                throw new RuntimeException("获取用户请求IP失败，可能被非法请求！");
            }
            // 用JWT制作token
            Map<String,Object> userMap = new HashMap<>();
            userMap.put("userId",userInfo.getId());
            userMap.put("nickname",userInfo.getNickname());
            // 对参数进行加密
            String md5Key = WebContents.md5Key;
            String userId = userInfo.getId();
            String md5EncodeUtf8 = MD5Util.MD5EncodeUtf8(realIP + "FA19C450FE8129143D920F447D321659");
            if (StringUtils.isNotBlank(md5EncodeUtf8)){
                // 加密后生成token
                token = JwtUtil.encode(md5Key, userMap, md5EncodeUtf8);
                // 将加密后生成的token存入缓存中
                Boolean addRedis = userService.addRedis(token,userId);
                if (!addRedis) System.out.println("****** token添加到缓存出错！ AuthCenterController的75行。。。");
            }else {
                token = "fail";
            }
        }else {
            // 登录失败
            token = "fail";
        }

        return token;
    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(@PathVariable("originUrl") String ReturnUrl, ModelMap modelMap){
        // 调用用户服务验证用户的信息

        modelMap.put("ReturnUrl", ReturnUrl);

        return "index";
    }

}
