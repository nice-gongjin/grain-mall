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

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Reference
    PaymentService paymentService;
    @Reference
    OrderService orderService;

    @RequestMapping(value = "/alipay", method = RequestMethod.GET)
    public String alibabaPay(String orderId, Model model) {
        if (StringUtils.isBlank(orderId)) {
            throw new MyException("订单ID不能为空！");
        }
        // 根据订单orderId获取订单信息
        OmsOrder orderInfo = orderService.getOrderInfo(orderId);
        if (null != orderInfo) {
            // 封装订单的属性返回给界面
            model.addAttribute("orderId", orderInfo.getId());
            model.addAttribute("totalAmount", orderInfo.getTotalAmount());

            return "paymentIndex";
        }
        return JSON.toJSONString(R.error("暂无订单信息！"));
    }

    @RequestMapping(value = "/wxpay", method = RequestMethod.GET)
    public String weixinPay(@RequestParam("orderId")String  orderId, Model model) {

        return "wxpay";
    }

}
