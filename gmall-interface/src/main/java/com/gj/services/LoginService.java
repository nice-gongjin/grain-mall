package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.UmsMember;

public interface LoginService extends IService<UmsMember> {

    UmsMember loginCheack(String loginName, String passwd);

}
