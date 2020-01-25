package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.gj.entitys.OmsOrder;
import com.gj.gmall.myException.MyException;
import com.gj.services.OrderService;
import com.gj.services.PaymentService;
import com.gj.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Reference
    PaymentService paymentService;
    @Reference
    OrderService orderService;

    // 支付宝支付
    @RequestMapping(value = "/alipay", method = RequestMethod.GET)
    public String alibabaPay(String orderId, HttpServletResponse response, Model model) {
        if (StringUtils.isBlank(orderId)) {
            throw new MyException("订单ID不能为空！");
        }
        // 根据订单orderId获取订单信息
        OmsOrder orderInfo = orderService.getOrderInfo(orderId);
        if (null != orderInfo) {
            // 封装订单的属性返回给界面
            model.addAttribute("orderId", orderInfo.getId());
            model.addAttribute("totalAmount", orderInfo.getTotalAmount());
            try {
                response.sendRedirect("payment.gmall.com:18888/finish.html");
            } catch (IOException e) {
                throw  new MyException("重定向到支付成功界面失败！");
            }
        }

        return JSON.toJSONString(R.error("支付失败！"));
    }

    // 微信支付
    @RequestMapping(value = "/wxpay", method = RequestMethod.GET)
    public String weixinPay(@RequestParam("orderId")String  orderId, HttpServletResponse response, Model model) {
        try {
            response.sendRedirect("payment.gmall.com:18888/finish.html");
        } catch (IOException e) {
            throw  new MyException("重定向到支付成功界面失败！");
        }

        return JSON.toJSONString(R.error("支付失败！"));
    }

}
