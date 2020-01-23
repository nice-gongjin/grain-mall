package com.gj.manage.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.OmsCartItem;
import com.gj.entitys.OmsOrder;
import com.gj.entitys.OmsOrderItem;
import com.gj.entitys.PmsSkuInfo;
import com.gj.manage.mapper.OrderInfoMapper;
import com.gj.manage.mapper.OrderMapper;
import com.gj.manage.mapper.SkuinfoMapper;
import com.gj.services.CartService;
import com.gj.services.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrderItem> implements OrderService {

    @Autowired
    SkuinfoMapper skuinfoMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Reference
    CartService cartService;

    @Override
    public Boolean cheackPrice(OmsCartItem cartItem) {
        boolean price = false;
        String productSkuId = cartItem.getProductSkuId();
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(productSkuId);
        PmsSkuInfo skuInfo = skuinfoMapper.selectOne(pmsSkuInfo);
        int compare = skuInfo.getPrice().compareTo(cartItem.getPrice());
        if (compare == 0) price = true;

        return price;
    }

    @Override
    public Integer cheackStock(String productSkuId) {

        return 1;
    }

    @Override
    public OmsOrder getOrderInfo(String orderId) {
        OmsOrder omsOrder = orderInfoMapper.selectById(orderId);

        return omsOrder;
    }

    @Override
    @Transactional
    public Boolean saveOrders(OmsOrder omsOrder) {
        boolean save = false;
        // 保存订单表
        Integer insert = orderInfoMapper.insert(omsOrder);
        if (insert > 0) {
            // 保存订单详情
            String orderId = omsOrder.getId();
            List<OmsOrderItem> omsOrderItems = omsOrder.getOmsOrderItems();
            if (null != omsOrderItems && omsOrderItems.size() > 0) {
                for (OmsOrderItem omsOrderItem : omsOrderItems) {
                    omsOrderItem.setOrderId(orderId);
                    Integer insert1 = orderMapper.insert(omsOrderItem);
                    // 保存成功一条就删除购物车中对应的数据
                    if (insert1 > 0) {
                        String userId = omsOrder.getMemberId();
                        cartService.deleteCarts(omsOrderItem, userId);
                    }
                }
            }
            save = true;
        }

        return save;
    }

}
