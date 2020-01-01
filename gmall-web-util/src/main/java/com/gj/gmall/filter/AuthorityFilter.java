package com.gj.gmall.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gj.gmall.annotation.LoginRequied;
import com.gj.gmall.utils.CookieUtil;
import com.gj.gmall.utils.HttpclientUtil;
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
        String newToken = request.getParameter("newToken");
        if(newToken != null && newToken.length() > 0) {
            CookieUtil.setCookie(request,response,"token",newToken,24*60*60,false);
        }
        // 判断被拦截的请求的访问的方法的注解是否是需要拦截的
        //1 进行如果能从cookie把token取出来，进行解析，显示页面上。
        String token = CookieUtil.getCookieValue(request, "token", false);
        String userId = null;
        if(token != null){
            Base64UrlCodec base64UrlCodec = new Base64UrlCodec();
            //  两个“.”之间的部分是实际内容
            String  tokenForDecode= StringUtils.substringBetween(token, ".");

            byte[] tokenByte = base64UrlCodec.decode(tokenForDecode);
            String tokenJson=new String(tokenByte,"UTF-8");
            System.out.println("tokenJson = " + tokenJson);

            JSONObject jsonObject = JSON.parseObject( tokenJson);

            userId = jsonObject.getString("userId");
            String nickName = jsonObject.getString("nickName");

            request.setAttribute("nickName",nickName);
        }

//        String tokenUserInfo = StringUtils.substringBetween(encode, ".");
//        Base64UrlCodec base64UrlCodec = new Base64UrlCodec();
//        byte[] tokenBytes = base64UrlCodec.decode(tokenUserInfo);
//        String tokenJson = null;
//        try {
//            tokenJson = new String(tokenBytes, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        Map map = JSON.parseObject(tokenJson, Map.class);
//        System.out.println("64="+map);

        //检查是否需要验证用户已经登录
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequied methodAnnotation = handlerMethod.getMethodAnnotation(LoginRequied.class);
        if(methodAnnotation != null){
            String currentIp = request.getHeader("x-forwarded-for");

            Map<String,String> map=new HashMap<>();
            map.put("currentIp", currentIp);
            map.put("token", token);
            String result = null;
            if(token != null) {
                result = HttpclientUtil.doPost("user,gmall,com", map);
            }
            if(result != null && result.equals("true")){
                request.setAttribute("userId",userId); //只有验证过才能取到userId
                return true;
            }else{
                if(methodAnnotation.loginSuccess()) {
                    String url = URLEncoder.encode(request.getRequestURL().toString(), "utf-8");

                    response.sendRedirect("login.gmall.com" + "?originUrl=" + url);
                    return false;
                }
            }
        }

        return true;
    }

}
