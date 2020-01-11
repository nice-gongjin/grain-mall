package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.PmsSkuInfo;
import com.gj.entitys.SearchParams;

import java.util.List;

public interface SearchService extends IService<PmsSkuInfo> {

    List<PmsSkuInfo> searchLists(SearchParams searchParams);

}
