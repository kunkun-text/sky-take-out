package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFIll;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface SetMealDishMapper {

    //修改对应套餐信息
    @AutoFIll(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    //根据菜品id批量查询是否关联套餐
    List<Long> getSetmealIdByDishId(List<Long> dishId);
    //新增套餐
    @AutoFIll(value = OperationType.INSERT)
    void insert(Setmeal setmeal);
    //新增套餐菜品关系
    void insertBatch(List<SetmealDish> setmealDishes);
    //套餐分页查询
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    //根据套餐id删除套餐菜品关系
    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);
    //根据套餐id查询套餐信息
    @Select("select * from setmeal where id = #{setmealId}")
    Setmeal selectById(Long setmealId);
    //根据套餐id查询套餐菜品
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getDishBySetmealId(Long setmealId);
    //批量删除对应套餐信息
    void deleteById(List<Long> setmealIds);
    //批量删除套餐中菜品信息
    void deleteBatch(List<Long> setmealIds);
    //起售/停售套餐
    void startOrStop(Setmeal setmeal);

    @Select("select * from setmeal where category_id = #{categoryId}")
    List<Setmeal> listByCategoryId(Integer categoryId);
    //根据套餐id查询菜品信息
    List<DishItemVO> selectByDishId(Long setmealId);
}
