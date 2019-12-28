package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gj.entitys.OmsCartItem;
import com.gj.entitys.PmsSkuInfo;
import com.gj.gmall.utils.CookieUtil;
import com.gj.services.CartService;
import com.gj.services.SkuinfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference
    private SkuinfoService skuinfoService;

    @Reference
    private CartService cartService;

    @RequestMapping(value = "/addToCart",method = RequestMethod.POST)
    public String addToCart(String skuId, int quantity, HttpServletRequest request, HttpServletResponse response){
        // 先查询商品信息
        PmsSkuInfo pmsSkuInfo = skuinfoService.selectById(skuId);
        // 将商品信息封装成购物车信息
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setModifyDate(new Date());
        omsCartItem.setPrice(pmsSkuInfo.getPrice());
        omsCartItem.setProductAttr("");
        omsCartItem.setProductBrand("");
        omsCartItem.setProductCategoryId(pmsSkuInfo.getCatalog3Id());
        omsCartItem.setProductId(pmsSkuInfo.getProductId());
        omsCartItem.setProductName(pmsSkuInfo.getSkuName());
        omsCartItem.setProductPic(pmsSkuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("666666");
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(quantity);
        // 判断是否有用户ID，有就登录状态，直接存到DB中，没有就未登录状态，存在cookie中
        String memberId = "";
        List<OmsCartItem> cartItemList = new ArrayList<>();
        // 判断用户是否登录
        if (StringUtils.isBlank(memberId)){
            // 用户未登录则将购物车的信息omsCartItem保存到cookie中
            // 判断浏览器的cookie中是否有这件商品
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isBlank(cartListCookie)){
                // 不存在就添加到cookie中
                cartItemList.add(omsCartItem);
            }else {
                // 存在就把这件skuId商品的数量属性quantity加1
                cartItemList = JSON.parseArray(cartListCookie, OmsCartItem.class);
                Boolean cartExits = if_cart_exits(cartItemList, skuId);
                if (cartExits) {
                    // cookie中存在skuId的商品
                    for (OmsCartItem cartItem : cartItemList) {
                        if (cartItem.getProductSkuId().equals(skuId)){
                            cartItem.setQuantity(cartItem.getQuantity() + omsCartItem.getQuantity());
                            // cartItem.setPrice(cartItem.getPrice().add(omsCartItem.getPrice()));
                        }
                    }
                } else {
                    // cookie中不存在skuId的商品
                    cartItemList.add(omsCartItem);
                }
                // 更新cookie，覆盖cookie之前的值
                CookieUtil.setCookie(request, response, "cartListCookie", JSON.toJSONString(cartItemList), 60 * 60 * 72, true);
            }
        }else {
            // 用户登录则奖购物车的信息omsCartItem保存到数据库和缓存中
            // 从数据库的购物车OmsCartItem表中出现数据
            OmsCartItem cartItems = cartService.selectByCart(memberId, skuId);
            System.out.println("****** cartItem = " + JSON.toJSONString(cartItems));
            if (cartItems == null){
                // 该用户memberId没有添加过商品到购物车
                omsCartItem.setMemberId(memberId);
                omsCartItem.setMemberNickname("test");
                omsCartItem.setQuantity(quantity);
                if(StringUtils.isBlank(omsCartItem.getMemberId())) return "请先登录！";
                else cartService.insert(omsCartItem);
            }else {
                // 该用户memberId添加过商品到购物车
                cartItems.setQuantity(omsCartItem.getQuantity());
                cartService.update(cartItems,new EntityWrapper<OmsCartItem>().eq("id",omsCartItem.getId()));
            }
            // 该用户memberId添加过商品到购物车
            Boolean flushCache = cartService.flushCache(memberId);
            if (!flushCache) return "fault";
        }

        return "redirect:/success.html";
    }

    @RequestMapping(value = "/cartList",method = RequestMethod.POST)
    public String cartList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String userId = "";
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        // 判断用户userId是否登录
        if (StringUtils.isBlank(userId)){
            // 用户未登录从cookie中查询购物车数据
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isNotBlank(cartListCookie)){
                List<OmsCartItem> cartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
            }
        }else {
            // 用户已经登录从缓存（redis）中查询购物车数据
            omsCartItems = cartService.getCartList(userId);
        }
        modelMap.put("cartList",omsCartItems);

        return "cartList";
    }

    /**
     * 判断购物车的cookie中是否存在skuId的商品信息
     * @return Boolean
     */
    private static Boolean if_cart_exits(List<OmsCartItem> cartItemList, String skuId){
        for (OmsCartItem cartItem : cartItemList) {
            // 判断cookie中是否存在skuId的商品
            if (cartItem.getProductSkuId().equals(skuId)) {
                return true;
            }
        }
        return false;
    }

}
