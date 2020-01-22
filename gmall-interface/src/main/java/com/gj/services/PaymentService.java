package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.PaymentInfo;

public interface PaymentService extends IService<PaymentInfo> {

    Boolean topicOrder();

    Boolean topicReorder();

    Boolean topicMessage();

}
