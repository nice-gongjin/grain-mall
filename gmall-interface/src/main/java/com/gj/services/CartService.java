package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.OmsCartItem;

import java.util.List;

public interface CartService extends IService<OmsCartItem> {

    OmsCartItem selectByCart(String memberId, String skuId);

    Boolean flushCache(String memberId);

    List<OmsCartItem> getCartList(String skuId);

}
