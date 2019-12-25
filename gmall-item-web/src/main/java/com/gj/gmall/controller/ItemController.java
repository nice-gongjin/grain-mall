package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gj.entitys.PmsSkuInfo;
import com.gj.services.SkuinfoService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Reference
    private SkuinfoService skuinfoService;

    @RequestMapping(value = "//{skuId}.html",method = RequestMethod.GET)
    public String index(@PathVariable("skuId") String skuId, Model model){
        System.out.println("****** skuId = " + skuId);
        PmsSkuInfo skuInfo = skuinfoService.getSkuInfo(skuId);
        model.addAttribute("skuInfo",skuInfo);

        return "item";
    }

}
