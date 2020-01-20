package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gj.services.PaymentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Reference
    PaymentService paymentService;

    @RequestMapping("/alipay")
    public String alibabaPay() {

        return "alipay";
    }

    @RequestMapping("/wxpay")
    public String weixinPay() {

        return "wxpay";
    }

}
