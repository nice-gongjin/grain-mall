package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.PmsSkuInfo;

public interface SkuinfoService extends IService<PmsSkuInfo> {
    PmsSkuInfo getSkuInfo(String skuId);
}
