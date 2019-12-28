package com.gj.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PmsSkuImage;
import com.gj.entitys.PmsSkuInfo;
import com.gj.entitys.PmsSkuSaleAttrValue;
import com.gj.manage.mapper.SkuSaleAttrValueMapper;
import com.gj.manage.mapper.SkuimageMapper;
import com.gj.manage.mapper.SkuinfoMapper;
import com.gj.services.SkuinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
public class SkuinfoServiceImpl extends ServiceImpl<SkuinfoMapper, PmsSkuInfo> implements SkuinfoService {

    @Autowired
    private SkuinfoMapper skuinfoMapper;

    @Autowired
    private SkuimageMapper skuimageMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuAttrValueMapper;

//    @Autowired
//    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public PmsSkuInfo getSkuInfo(String skuId) {
        // sku的数据
        PmsSkuInfo skuInfo = skuinfoMapper.selectById(skuId);
        if(skuInfo==null){
            return null;
        }
        // sku的图片集合
        PmsSkuImage skuImage=new PmsSkuImage();
        skuImage.setSkuId(skuId);
        List<PmsSkuImage> skuImageList = skuimageMapper.selectList(new EntityWrapper<>(skuImage));
        skuInfo.setPmsSkuImageList(skuImageList);
        // sku的属性集合
        PmsSkuSaleAttrValue skuSaleAttrValue=new PmsSkuSaleAttrValue();
        skuSaleAttrValue.setSkuId(skuId);
        EntityWrapper<PmsSkuSaleAttrValue> skuSaleAttrValueEntity = new EntityWrapper<>(skuSaleAttrValue);
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = skuAttrValueMapper.selectList(skuSaleAttrValueEntity);
        skuInfo.setPmsSkuSaleAttrValueList(skuSaleAttrValueList);

        return  skuInfo;
    }

}
