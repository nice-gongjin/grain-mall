package com.gj.gmall.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/search")
public class SearchController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(String skuId, HttpServletRequest request, ModelMap modelMap){
        System.out.println("****** skuId = " + skuId);

        return "search";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        System.out.println("****** skuId = ");

        return "search";
    }

}
