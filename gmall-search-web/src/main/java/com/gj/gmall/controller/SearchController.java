package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gj.entitys.PmsSkuInfo;
import com.gj.entitys.SearchParams;
import com.gj.gmall.annotation.LoginRequied;
import com.gj.services.SearchService;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/search")
public class SearchController {

    @Reference
    SearchService searchService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(String Return, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        System.out.println("****** skuId = " + Return);

        try {
            response.sendRedirect("http://auth.gmall.com:10022/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:index.html";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(SearchParams searchParams, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        System.out.println("****** keyword = " + searchParams);

        List<PmsSkuInfo> pmsSkuInfos = searchService.searchLists(searchParams);
        modelMap.put("skuLsInfoList", pmsSkuInfos);

        try {
            response.sendRedirect("http://auth.gmall.com:10022/list.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "list";
    }

    @LoginRequied(loginSuccess = true)
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@Valid @Max(value = 5, message = "不能超过5") Integer id){
        System.out.println("****** keyword = " + id);
        return String.valueOf(id);
    }

}
