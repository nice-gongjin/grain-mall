package com.gj.userservice.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gj.entitys.UmsMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<UmsMember> {

    @Select("select username,password from ums_member")
    List<Object> getUsers();

}
