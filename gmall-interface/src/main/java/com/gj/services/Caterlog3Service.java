package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.PmsBaseCatalog3;
import com.gj.util.PageUtils;

public interface Caterlog3Service extends IService<PmsBaseCatalog3> {

    PageUtils getCaterlogs3();

}
