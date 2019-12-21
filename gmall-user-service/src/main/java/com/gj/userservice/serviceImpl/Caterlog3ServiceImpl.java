package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PmsBaseCatalog3;
import com.gj.services.Caterlog3Service;
import com.gj.userservice.mapper.Caterlog3Mapper;
import com.gj.util.PageUtils;
import com.gj.util.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Caterlog3ServiceImpl extends ServiceImpl<Caterlog3Mapper,PmsBaseCatalog3> implements Caterlog3Service {

    @Reference
    private Caterlog3Mapper caterlog3Mapper;

    @Override
    public PageUtils getCaterlogs3() {
        Map<String,Object> maps = new HashMap<>();
        List<PmsBaseCatalog3> catalog1s = caterlog3Mapper.selectList(null);
        maps.put("caterlog3", catalog1s);
        Page<PmsBaseCatalog3> page = this.selectPage(
                new Query<PmsBaseCatalog3>(maps).getPage(),
                new EntityWrapper<PmsBaseCatalog3>()
        );

        return new PageUtils(page);
    }
}
