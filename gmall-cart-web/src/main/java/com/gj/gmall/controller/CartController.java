package com.gj.gmall.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String get(){
        return "redirect:/success.html";
    }

}
