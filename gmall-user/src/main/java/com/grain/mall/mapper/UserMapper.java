package com.grain.mall.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper{

    @Select("select username,password from ums_member")
    List<Object> getUsers();

}
