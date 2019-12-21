package com.gj.userservice.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gj.entitys.UmsMember;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface User2Mapper extends BaseMapper<UmsMember> {

    @Select("select username,password from ums_member")
    List<Object> getUsers();

}
