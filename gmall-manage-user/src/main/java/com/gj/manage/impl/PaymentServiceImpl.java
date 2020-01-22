package com.gj.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PaymentInfo;
import com.gj.manage.mapper.PaymentMapper;
import com.gj.services.PaymentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentInfo> implements PaymentService {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    PaymentMapper paymentMapper;

    @Override
    public Boolean topicOrder() {
        //参数介绍： 交换机名字，路由建， 消息内容
        rabbitTemplate.convertAndSend("topicExchange","topic.order","hello");
        return true;
    }

    @Override
    public Boolean topicReorder() {
        return true;
    }

    @Override
    public Boolean topicMessage() {
        return true;
    }

}
