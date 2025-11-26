package com.sky.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SetMealDishMapper {

    //根据菜品id批量查询是否关联套餐
    List<Long> getSetmealIdByDishId(List<Long> dishId);
}
