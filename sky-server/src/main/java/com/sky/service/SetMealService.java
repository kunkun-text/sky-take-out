package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {

    //新增套餐
    void addSetmeal(SetmealDTO setmealDTO);
    //分页查询套餐
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    //修改套餐
    void updateSetmeal(SetmealDTO setmealDTO);
    //根据id查询套餐信息
    SetmealVO getSetmealById(Long setmealId);
    //批量删除套餐
    void deleteSetmeal(List<Long> setmealIds);
    //起售停售套餐
    void startOrStop(Integer status, Long id);
    //根据分类id查询对应菜品
    List<Setmeal> listByCategoryId(Integer categoryId);
    //根据套餐id查询菜品信息
    List<DishItemVO> getDishBySetmealId(Long setmealId);
}
