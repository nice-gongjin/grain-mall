package com.gj.gmall.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gj.gmall.annotation.LoginRequied;
import com.gj.gmall.utils.CookieUtil;
import com.gj.util.HttpclientUtil;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorityFilter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否需要对请求进行拦截
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequied methodAnnotation = handlerMethod.getMethodAnnotation(LoginRequied.class);
        if(methodAnnotation == null){
            // methodAnnotation为空表示不需要对请求进行拦截
            // 这里把不需要验证但是需要cookie的请求方法加上注解并把值设为false
            return true;
        } else {
            // methodAnnotation不为空表示需要对请求进行拦截
            String userId = "1";
            // token
            String token = null;
            // 从cookie把token取出来进行解析
            String oldToken = CookieUtil.getCookieValue(request, "token", false);
            if (StringUtils.isNotBlank(oldToken)){
                token = oldToken;
            }
            // 从地址中获取进行解析
            String newToken = request.getParameter("token");
            if (StringUtils.isNotBlank(newToken)){
                token = newToken;
            }
            // 判断是否需要用户登录
            boolean loginSuccess = methodAnnotation.loginSuccess();
            // 调用认证中心进行验证
            String result = null;
            Map<String, String> successMap = new HashMap<>();
            if (StringUtils.isNotBlank(token)){
                // 调用验证方法
                String realIP = null;
                String xff = request.getHeader("x-forwarded-for");
                if (StringUtils.isNotBlank(xff)) {
                    realIP = xff;
                }else {
                    realIP = request.getRemoteAddr();
                }
                if (StringUtils.isBlank(realIP)) {
                    System.out.println("****** 获取用户请求IP失败！ AuthorityFilter类中第62行。。。");
                    throw new RuntimeException("获取用户请求IP失败，可能被非法请求！");
                }
                String successJson = HttpclientUtil.
                        doGet("Http://auth.gmall.com:10022/auth/verify?token=" + token + "&currentIP=" + realIP);
                // 将successJson转化为Map
                if (StringUtils.isNotBlank(successJson)) {
                    successMap = JSON.parseObject(successJson, Map.class);
                    result = successMap.get("status");
                }
            }
            if (loginSuccess){
                // 需要用户登录
                if(StringUtils.isNotBlank(result) && result.equals("success")){
                    // 验证通过，将token覆盖，并将token携带的用户信息写回
                    request.setAttribute("userId", successMap.get("memberId"));
                    request.setAttribute("nickname", successMap.get("nickname"));
                    // 验证通过，覆盖cookie中的token
                    if (StringUtils.isNotBlank(token)){
                        CookieUtil.setCookie(request,response,"oldToken",token,24*60*60,true);
                    }
                }else{
                    // 重定向到登录界面
                    response.sendRedirect("Http://auth.gmall.com:10022/auth/index?Return=" + request.getRequestURL());
                    return false;
                }
            } else {
                // 不需要用户登录，但是必须验证
                if ("success".equals(result)){
                    request.setAttribute("userId", successMap.get("memberId"));
                    request.setAttribute("nickname", successMap.get("nickname"));
                    // 验证通过，覆盖cookie中的token
                    if (StringUtils.isNotBlank(token)){
                        CookieUtil.setCookie(request,response,"oldToken",token,24*60*60,true);
                    }
                }
            }
        }

        return true;
    }

}
