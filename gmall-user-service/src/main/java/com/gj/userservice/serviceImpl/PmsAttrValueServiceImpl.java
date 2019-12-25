package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PmsBaseAttrValue;
import com.gj.services.PmsAttrValueService;
import com.gj.userservice.mapper.PmsAttrValueMapper;

@Service
public class PmsAttrValueServiceImpl extends ServiceImpl<PmsAttrValueMapper, PmsBaseAttrValue> implements PmsAttrValueService {
}
