package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface dishMapper {

    //根据id查询菜品数量
    @Select("select count(id) from dish where category_id = #{id}")
    Integer CountById(Integer id);
}
