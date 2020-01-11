package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gj.entitys.OmsCartItem;
import com.gj.gmall.annotation.LoginRequied;
import com.gj.services.CartService;
import com.gj.services.UserService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    CartService cartService;

    @Reference
    UserService userService;

    /**
     * 购物车列表界面对购物车的数量的异步修改
     */
    @RequestMapping(value = "/trade",method = RequestMethod.POST)
    @LoginRequied(loginSuccess = true)
    public String toTrade(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        // 判断用户是否登录
        String userId = (String) request.getAttribute("memberId");
        String nickname = (String) request.getAttribute("nickname");

        // 将购物车的集合转化为订单页面的计算清单集合
        List<OmsCartItem> cartLists = cartService.getCartList(userId);
        if (null != cartLists && cartLists.size() > 0) {
            for (OmsCartItem cartList : cartLists) {
            }
        }

        return "toTrade";
    }

}
