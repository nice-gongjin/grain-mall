package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gj.entitys.PmsSkuInfo;
import com.gj.services.SkuinfoService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gj
 *  这里主要根据某个商品的id去查询这个商品的商品属性及图片等信息给前端；以及处理界面的一些事件的处理
 */
@RestController
@RequestMapping("/item")
public class ItemController {

//    @Reference
//    private SkuinfoService skuinfoService;
//
//    @RequestMapping(value = "/{skuId}.html", method = RequestMethod.GET)
//    public String index(@PathVariable("skuId") String skuId, Model model){
//        System.out.println("****** skuId = " + skuId);
//        PmsSkuInfo skuInfo = skuinfoService.getSkuInfo(skuId);
//        model.addAttribute("skuInfo",skuInfo);
//
//        return "item";
//    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request){
        System.out.println("当前线程名称" + Thread.currentThread().getName() + " IP: " + request.getSession().toString());
        return "success";
    }

}
