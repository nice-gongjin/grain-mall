package com.gj.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gj.services.CaterlogService;
import com.gj.util.PageUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caterlog")
public class CaterlogController {

    @Reference
    private CaterlogService caterlogService;

    @RequestMapping("/list")
    public PageUtils get(){
        return caterlogService.getCaterlogs();
    }

}
