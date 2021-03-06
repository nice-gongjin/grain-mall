package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.gj.entitys.UmsMember;
import com.gj.gmall.myException.MyException;
import com.gj.gmall.utils.CookieUtil;
import com.gj.gmall.utils.JwtUtil;
import com.gj.gmall.utils.MD5Util;
import com.gj.gmall.webContents.WebContents;
import com.gj.services.UserService;
import com.gj.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
public class AuthCenterController {

    @Reference
    UserService userService;

    // 验证token
    // 如果这里是从连接器AuthorityFilter类中发送过来的请求，则IP是连接器服务器的IP，故要接收传过了的currentIP
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String verify(@PathVariable("token") String token, @PathVariable("currentIP") String IP){
        // 通过token验证用户身份
        // 从请求或者代理中获取用户的请求IP，当做JWT加密的盐值的一部分
        // （getRemoteAddr()：表示直接从浏览器的请求中获取用户IP，getHeader("x-forwarded-for")：表示从代理服务中获取用户IP）
        ConcurrentHashMap<String, String> successMap = null;
        if (StringUtils.isNotBlank(token)){
            String md5Key = WebContents.md5Key;
            String md5EncodeUtf8 = MD5Util.MD5EncodeUtf8(IP) + "FA19C450FE8129143D920F447D321659";
            Map<String, Object> decode = JwtUtil.decode(token,md5Key,md5EncodeUtf8);
            if (null != decode){
                successMap = new ConcurrentHashMap<>();
                successMap.put("status", "success");
                successMap.put("userId", "memberId");
                successMap.put("nickname", "nickname");
            }

            return JSON.toJSONString(successMap);
        }
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(UmsMember umsMember, HttpServletRequest request) {
        String token = "";
        try {
            // 先将密码加密，因为数据库保存的用户密码都是MD5加密后的密码
            umsMember.setPassword(MD5Util.MD5Encode(umsMember.getPassword(), "utf-8"));
            // 调用用户服务验证用户的信息
            UmsMember userInfo = userService.login(umsMember);
            if (userInfo != null) {
                // 登录成功
                // 从请求或者代理中获取用户的请求IP，当做JWT加密的盐值的一部分
                // （getRemoteAddr()：表示直接从浏览器的请求中获取用户IP，getHeader("x-forwarded-for")：表示从代理服务中获取用户IP）
                String realIP = null;
                String xff = request.getHeader("x-forwarded-for");
                if (StringUtils.isNotBlank(xff)) {
                    //realIP = xff;
                    int index = xff.indexOf(",");
                    if (-1 != index) {
                        xff = xff.substring(0, index);
                    }
                    System.out.println("****** xff222 = " + xff + "  ******  " + xff.trim());
                    realIP = xff.trim();
                } else {
                    realIP = request.getRemoteAddr();
                }
                System.out.println("****** xff = " + xff);
                if (StringUtils.isBlank(realIP)) {
                    System.out.println("****** 获取用户请求IP失败！ AuthCenterController类中第74行。。。");
                    throw new RuntimeException("获取用户请求IP失败，可能被非法请求！");
                }
                // 用JWT制作token
                ConcurrentHashMap<String, Object> userMap = new ConcurrentHashMap<>();
                userMap.put("userId", userInfo.getId());
                userMap.put("nickname", userInfo.getNickname());
                // 对参数进行加密
                String md5Key = WebContents.md5Key;
                String userId = userInfo.getId();
                String md5EncodeUtf8 = MD5Util.MD5EncodeUtf8(realIP) + "FA19C450FE8129143D920F447D321659";
                if (StringUtils.isNotBlank(md5EncodeUtf8)) {
                    // 加密后生成token
                    token = JwtUtil.encode(md5Key, userMap, md5EncodeUtf8);
                    // 将加密后生成的token存入缓存中
                    Boolean addRedis = userService.addRedis(token, userId);
                    if (!addRedis) {
                        System.out.println("****** token添加到缓存出错！ AuthCenterController的75行。。。");
                    }
                } else {
                    token = "fail";
                }
            } else {
                // 登录失败
                token = "fail";
            }
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        return token;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("originUrl") String ReturnUrl, HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView();
        // 调用用户服务删除缓存中的用户信息和Cookie中的token
        String token = CookieUtil.getCookieValue(request, "token", false);
        if (null != token) {
            CookieUtil.deleteCookie(request, response, "token");
            boolean logout = userService.logOut(request, response);
            if (logout) {
                modelAndView.addObject("data", R.ok("注销成功！"));
            } else {
                modelAndView.addObject("data", R.error("注销失败！"));
            }
            /*modelAndView.setViewName("static/index");*/
            modelAndView.setViewName("redirect:" + ReturnUrl);
        } else {
            modelAndView.setViewName("redirect:https://login.gmall.com:10086/index.html");
        }

        return modelAndView;
    }

}
