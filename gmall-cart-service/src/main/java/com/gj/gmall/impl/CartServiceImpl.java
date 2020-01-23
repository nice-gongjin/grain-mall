package com.gj.gmall.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.OmsCartItem;
import com.gj.entitys.OmsOrderItem;
import com.gj.gmall.mapper.CartMapper;
import com.gj.gmall.utils.MapSortUtil;
import com.gj.services.CartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
            cartItem.setTotalPrice(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            map.put(cartItem.getProductSkuId(), JSON.toJSONString(cartItem));
        }
        try{
            if (null == redisTemplate) return false;
            // 添加到缓存前先删除缓存中已经存在的数据
            Set<Object> keys = redisTemplate.opsForHash().keys("user:" + memberId + ":cart");
            Long delete = redisTemplate.opsForHash().delete("user:" + memberId + ":cart", keys);
            // 保存前先将map变为按key降序排序的map集合
            Map<String, Object> sort = MapSortUtil.sortByKey(map, false);
            // 将map封装为一个String保存到缓存redis中
            if (delete > 0) redisTemplate.opsForHash().putAll("user:" + memberId + ":cart", sort);
            else return false;
        }catch (Exception e){
            System.out.println("****** 刷新redis缓存出错！ 位置：CartServiceImpl类中第56行输出！");
            return false;
        }

        return true;
    }

    @Override
    public List<OmsCartItem> getCartList(String userId) {
        List<OmsCartItem> list = new ArrayList<>();
        try {
            if (null == redisTemplate) return null;
            // 从缓存redis中根据key获取value的集合，value封装的是一个String类型的Map<OmsCartItem的ProductSkuId,String类型的OmsCartItem>
            List<Object> values = redisTemplate.opsForHash().values("user:" + userId + ":cart");
            if (values.size() > 0){
                for (Object value : values) {
                    // value是一条String类型的OmsCartItem
                    OmsCartItem omsCartItem = JSON.parseObject(String.valueOf(value), OmsCartItem.class);
                    list.add(omsCartItem);
                }
            }
        }catch (Exception e) {
            System.out.println("****** CartServiceImpl第80行... error = " + e.getMessage());
            return null;
        }

        return list;
    }

    @Override
    public Boolean cartCheked(String userId, String skuId, String isChecked) {
        // 根据参数从数据库中修改商品为skuId的数据
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(userId);
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setIsChecked(isChecked);
        Integer update = cartMapper.update(omsCartItem,
                new EntityWrapper<OmsCartItem>().eq("memberId", userId).eq("productSkuId", skuId));
        if (update > 0) {
            Boolean aBoolean = flushCache(userId);
            if (!aBoolean) {
                // 如果同步数据到缓存失败
                System.out.println("****** 同步数据到缓存失败！ \n 位置在：" + "CartServiceImpl类中的cartCheked方法中（91行）");
                return false;
            }
            return true;
        } else return false;
    }

    @Override
    public Boolean cartQuantity(String userId, String skuId, String quantity) {
        // 根据参数从数据库中修改商品为skuId的数据
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(userId);
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(Integer.valueOf(quantity));
        omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(BigDecimal.valueOf(omsCartItem.getQuantity())));
        Integer update = cartMapper.update(omsCartItem,
                new EntityWrapper<OmsCartItem>().eq("memberId", userId).eq("productSkuId", skuId));
        if (update > 0) {
            Boolean aBoolean = flushCache(userId);
            if (!aBoolean) {
                // 如果同步数据到缓存失败
                System.out.println("****** 同步数据到缓存失败！ \n 位置在：" + "CartServiceImpl类中的cartQuantity方法中（111行）");
                return false;
            }
            return true;
        } else return false;
    }

    @Override
    public Boolean addTradeCode(String userId, String tradeCode) {
        ValueOperations<String, Object> redis = redisTemplate.opsForValue();
        try {
            if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(tradeCode)) {
                String value = (String) redis.get("trade:" + userId + ":code");
                if (StringUtils.isBlank(value)) {
                    redis.set("trade:" + userId + ":code", tradeCode,30, TimeUnit.MINUTES);
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("****** e= " + e.getMessage());
        }
        return false;
    }

    @Override
    public void deleteCarts(OmsOrderItem omsOrderItem, String userId) {
        Integer delete = cartMapper.delete(new EntityWrapper<OmsCartItem>()
                .eq("productId", omsOrderItem.getProductId())
                .eq("productSkuId", omsOrderItem.getProductSkuId())
        );
        if (delete > 0) {
            if (StringUtils.isNotBlank(userId) && null != redisTemplate) {
                Boolean hasKey = redisTemplate.hasKey("trade:" + userId + ":code");
                if (hasKey) {
                    redisTemplate.delete("trade:" + userId + ":code");
                }
            }
        }
    }

}
