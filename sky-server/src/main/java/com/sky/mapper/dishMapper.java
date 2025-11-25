package com.sky.mapper;

import com.sky.annotation.AutoFIll;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface dishMapper {

    //根据id查询菜品数量
    @Select("select count(id) from dish where category_id = #{id}")
    Integer CountById(Integer id);

    //插入菜品数据
    @AutoFIll(value = OperationType.INSERT)
    void insert(Dish dish);
}
