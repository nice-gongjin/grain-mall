package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.PmsBaseCatalog2;
import com.gj.util.PageUtils;

public interface Caterlog2Service extends IService<PmsBaseCatalog2> {

    PageUtils getCaterlogs2();

}
