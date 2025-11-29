package com.sky.mapper;

import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface setMealMapper {

    //根据id查询套餐数量
    @Select("select count(id) from setmeal where category_id = #{id}")
    Integer CountById(Integer id);

    //根据id查询套餐详情
    @Select("select * from setmeal where id = #{setmealId}")
    Setmeal selectById(Long setmealId);
}
