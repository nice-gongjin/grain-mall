package com.gj.manage.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PmsBaseCatalog1;
import com.gj.manage.mapper.CaterlogMapper;
import com.gj.services.CaterlogService;
import com.gj.util.PageUtils;
import com.gj.util.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaterlogServiceImpl extends ServiceImpl<CaterlogMapper,PmsBaseCatalog1> implements CaterlogService {
    @Override
    public PageUtils getCaterlogs() {
        List<Object> list = new ArrayList<>();
        Map<String,Object> maps = new HashMap<>();
        maps.put("id", 1);
        maps.put("name", "CaterlogServiceImpl");
        maps.put("age", 23);
        maps.put("sex", "男");
        list.add(maps);

        Page<PmsBaseCatalog1> page = this.selectPage(
                new Query<PmsBaseCatalog1>(maps).getPage(),
                new EntityWrapper<PmsBaseCatalog1>()
        );

        return new PageUtils(page);
    }
}
