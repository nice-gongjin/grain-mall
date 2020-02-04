package com.gj.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.gj.entitys.OmsOrder;
import com.gj.gmall.myException.MyException;
import com.gj.services.OrderService;
import com.gj.services.PaymentService;
import com.gj.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/pay")
public class PaymentController {

//    @Reference
//    PaymentService paymentService;
    @Reference
    OrderService orderService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    AmqpTemplate amqpTemplate;

    // 微信和支付宝支付参考： F:\myProjects\wechat-upload\src\main\java\com\cherlshall\wechat

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
        if (StringUtils.isNotBlank(orderId)) {
            // 构建消息
            String msg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            byte[] msgBytes = ("测试延迟队列: " + msg).getBytes();
            Message messager = MessageBuilder.withBody(msgBytes)
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .setMessageId(UUID.randomUUID().toString().replaceAll("-", ""))
                    .build();
            String msgId = UUID.randomUUID().toString();
            CorrelationData correlationData = new CorrelationData(msgId);
            // 持久性 non-persistent (1) or persistent (2)
            //messager.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            // 设置延迟的毫秒数
            messager.getMessageProperties().setDelay(5000);
            System.out.println(5 + "秒后, 发送延迟消息，当前时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //发送消息
            if ("1".equals(orderId)) {
                rabbitTemplate.convertAndSend("topicExchange", "topic.order", messager, correlationData);
            } else if ("2".equals(orderId)) {
                rabbitTemplate.convertAndSend("topicExchange", "topic.reorder", messager, correlationData);
            } else if ("3".equals(orderId)) {
                rabbitTemplate.convertAndSend("topicExchange", "topic.stock", messager, correlationData);
            } else if ("4".equals(orderId)) {
                // 创建处理延时消息
                // 动态设置TTL过期时间
                messager.getMessageProperties().setExpiration(String.valueOf(10 * 1000));
                // 此条消息在10秒后会过期并转发到topic.error.msg的队列中消费
                rabbitTemplate.convertAndSend("topicExchange", "topic.dead.queue", messager, correlationData);
            } else {
                //第一个参数是前面RabbitMqConfig的交换机名称 第二个参数的路由名称 第三个参数是传递的参数 第四个参数是配置属性
                rabbitTemplate.convertAndSend("topicExchange", "topic.error.msg", msg, message -> {
                    message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
                    message.getMessageProperties().setMessageId(UUID.randomUUID().toString().replaceAll("-", ""));
                    //配置消息的延迟时间
                    message.getMessageProperties().setDelay(5000);
                    return message;
                });
            }
            try {
                response.sendRedirect("payment.gmall.com:18888/finish.html");
            } catch (IOException e) {
                throw new MyException("重定向到支付成功界面失败！");
            }
        }

        return JSON.toJSONString(R.error("支付失败！"));
    }

}
