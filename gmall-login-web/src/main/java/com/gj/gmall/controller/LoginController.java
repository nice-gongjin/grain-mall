package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.gj.entitys.UmsMember;
import com.gj.gmall.utils.CookieUtil;
import com.gj.services.LoginService;
import com.gj.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gj
 *  登录模块我放到auth-center模块中了，后面可能会调整到这个模块中
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    LoginService loginService;

    @RequestMapping(value = "/in",method = RequestMethod.POST)
    public String loginIn(String loginName, String passwd, String originUrl,
                          HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        if (StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(passwd)) {
            UmsMember loginCheack = loginService.loginCheack(loginName, passwd);
            if (null != loginCheack) {
                // 设置cokie的值
                // 定义cookie的存活时间,单位秒
                int cookieAge = 60 * 60 * 72;
                CookieUtil.setCookie(request, response, "loginList", JSON.toJSONString(loginCheack), cookieAge, true);
                if (StringUtils.isBlank(originUrl)) {
                    originUrl = "http://item.gmall.com:10019/index.html";
                }
                try {
                    response.sendRedirect(originUrl);
                    return "success";
                } catch (IOException e) {
                    System.out.println("登录成功后重定向失败！");
                }
            }
        }

        return JSON.toJSONString(R.error("用户名或密码不能为空！"));
    }

    @RequestMapping(value = "/off",method = RequestMethod.POST)
    public void loginOff(String originUrl, HttpServletRequest request, HttpServletResponse response){
        // 删除cookie
        CookieUtil.deleteCookie(request, response, "loginList");
        // 返回originUrl页面
        if (StringUtils.isBlank(originUrl)) {
            // 如果返回页面为空就直接重定向到道路界面
            originUrl = "http://login.gmall.com:10008/index.html";
        }
        try {
            response.sendRedirect(originUrl);
        } catch (IOException e) {
            System.out.println("退出登录后重定向页面错误！");
        }
    }

}
