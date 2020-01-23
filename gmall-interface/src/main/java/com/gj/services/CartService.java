package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.OmsCartItem;
import com.gj.entitys.OmsOrderItem;

import java.util.List;

public interface CartService extends IService<OmsCartItem> {

    Boolean cartQuantity(String userId, String skuId, String quantity);

    OmsCartItem selectByCart(String memberId, String skuId);

    Boolean flushCache(String memberId);

    List<OmsCartItem> getCartList(String skuId);

    Boolean cartCheked(String userId, String skuId, String isChecked);

    Boolean addTradeCode(String userId, String tradeCode);

    void deleteCarts(OmsOrderItem omsOrderItem, String userId);

}
