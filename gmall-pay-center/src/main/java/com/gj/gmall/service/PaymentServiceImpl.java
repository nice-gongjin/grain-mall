package com.gj.gmall.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PaymentInfo;
import com.gj.gmall.mapper.PaymentMapper;
import com.gj.services.PaymentService;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentInfo> implements PaymentService {
}
