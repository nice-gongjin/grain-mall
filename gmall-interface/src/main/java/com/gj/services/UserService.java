package com.gj.services;

import com.baomidou.mybatisplus.service.IService;
import com.gj.entitys.UmsMember;

import java.util.List;

public interface UserService extends IService<UmsMember> {

    List<Object> getUsers();

}
