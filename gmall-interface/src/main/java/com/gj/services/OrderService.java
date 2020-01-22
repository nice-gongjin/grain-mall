package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.OmsCartItem;
import com.gj.entitys.OmsOrder;
import com.gj.entitys.OmsOrderItem;

import java.math.BigDecimal;

public interface OrderService extends IService<OmsOrderItem> {

    Boolean cheackPrice(OmsCartItem omsCartItem);

    Integer cheackStock(String productSkuId);

    OmsOrder getOrderInfo(String orderId);

}
