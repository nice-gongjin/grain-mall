package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PmsBaseCatalog2;
import com.gj.services.Caterlog2Service;
import com.gj.userservice.mapper.Caterlog2Mapper;
import com.gj.util.PageUtils;
import com.gj.util.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Caterlog2ServiceImpl extends ServiceImpl<Caterlog2Mapper,PmsBaseCatalog2> implements Caterlog2Service {

    @Reference
    private Caterlog2Mapper caterlog2Mapper;

    @Override
    public PageUtils getCaterlogs2() {
        Map<String,Object> maps = new HashMap<>();
        List<PmsBaseCatalog2> catalog1s = caterlog2Mapper.selectList(null);
        maps.put("caterlog2", catalog1s);
        Page<PmsBaseCatalog2> page = this.selectPage(
                new Query<PmsBaseCatalog2>(maps).getPage(),
                new EntityWrapper<PmsBaseCatalog2>()
        );

        return new PageUtils(page);
    }

}
