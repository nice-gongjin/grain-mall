package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PmsBaseCatalog1;
import com.gj.services.CaterlogService;
import com.gj.userservice.mapper.CaterlogMapper;
import com.gj.util.PageUtils;
import com.gj.util.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaterlogServiceImpl extends ServiceImpl<CaterlogMapper,PmsBaseCatalog1> implements CaterlogService {

    @Reference
    CaterlogMapper caterlogMapper;

    @Override
    public PageUtils getCaterlogs() {
        Map<String,Object> maps = new HashMap<>();
        List<PmsBaseCatalog1> catalog1s = caterlogMapper.selectList(null);
        maps.put("caterlog", catalog1s);
        Page<PmsBaseCatalog1> page = this.selectPage(
                new Query<PmsBaseCatalog1>(maps).getPage(),
                new EntityWrapper<PmsBaseCatalog1>()
        );

        return new PageUtils(page);
    }

}
