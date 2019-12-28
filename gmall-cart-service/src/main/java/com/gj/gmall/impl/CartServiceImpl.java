package com.gj.gmall.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.OmsCartItem;
import com.gj.gmall.mapper.CartMapper;
import com.gj.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, OmsCartItem> implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public OmsCartItem selectByCart(String memberId, String skuId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        OmsCartItem selectOne = cartMapper.selectOne(omsCartItem);

        return selectOne;
    }

    @Override
    public Boolean flushCache(String memberId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        // 从数据库中查询出所有属于memberId的数据
        List<OmsCartItem> omsCartItems = cartMapper.selectList(new EntityWrapper<>(omsCartItem).eq("memberId",memberId));
        // 将属于memberId的所有购物车数据封装在map中
        Map<String,Object> map = new HashMap<>();
        for (OmsCartItem cartItem : omsCartItems) {
            map.put(cartItem.getProductSkuId(), JSON.toJSONString(cartItem));
        }
        try{
            // 将map封装为一个String保存到缓存redis中
            redisTemplate.opsForHash().putAll("user:" + memberId + ":cart", map);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public List<OmsCartItem> getCartList(String userId) {
        List<OmsCartItem> list = new ArrayList<>();
        // 从缓存redis中根据key获取value的集合，value封装的是一个String类型的Map<OmsCartItem的ProductSkuId,String类型的OmsCartItem>
        List<Object> values = redisTemplate.opsForHash().values("user:" + userId + ":cart");
        if (values.size() > 0){
            for (Object value : values) {
                // value是一条String类型的OmsCartItem
                OmsCartItem omsCartItem = JSON.parseObject(String.valueOf(value), OmsCartItem.class);
                list.add(omsCartItem);
            }
        }

        return list;
    }

}
