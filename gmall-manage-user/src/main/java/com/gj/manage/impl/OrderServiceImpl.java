package com.gj.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.OmsCartItem;
import com.gj.entitys.OmsOrderItem;
import com.gj.entitys.PmsSkuInfo;
import com.gj.manage.mapper.OrderMapper;
import com.gj.manage.mapper.SkuinfoMapper;
import com.gj.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrderItem> implements OrderService {

    @Autowired
    SkuinfoMapper skuinfoMapper;

    @Override
    public Boolean cheackPrice(OmsCartItem cartItem) {
        String productSkuId = cartItem.getProductSkuId();
        PmsSkuInfo skuInfo = skuinfoMapper.selectById(productSkuId);
        boolean equals = skuInfo.getPrice().equals(cartItem.getPrice());

        return equals;
    }

    @Override
    public Integer cheackStock(String productSkuId) {

        return 0;
    }

}
