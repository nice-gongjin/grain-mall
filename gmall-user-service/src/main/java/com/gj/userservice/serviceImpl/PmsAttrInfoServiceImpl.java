package com.gj.userservice.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gj.entitys.PmsBaseAttrInfo;
import com.gj.services.PmsAttrInfoService;
import com.gj.userservice.mapper.PmsAttrInfoMapper;

@Service
public class PmsAttrInfoServiceImpl extends ServiceImpl<PmsAttrInfoMapper, PmsBaseAttrInfo> implements PmsAttrInfoService {
}
