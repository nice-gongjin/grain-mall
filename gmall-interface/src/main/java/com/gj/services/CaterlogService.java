package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.PmsBaseCatalog1;
import com.gj.util.PageUtils;

public interface CaterlogService extends IService<PmsBaseCatalog1> {

    PageUtils getCaterlogs();

}
