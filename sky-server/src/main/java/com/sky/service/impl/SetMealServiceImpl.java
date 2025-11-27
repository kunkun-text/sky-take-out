package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealDishMapper setMealDishMapper;

    //新增套餐
    @Transactional
    public void  addSetmeal(SetmealDTO setmealDTO){

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //新增套餐信息
        setMealDishMapper.insert(setmeal);
        //获取inset生成的id
        Long setmealId = setmeal.getId();
        //新增套餐菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        //插入套餐菜品关系
        setMealDishMapper.insertBatch(setmealDishes);
    }
    //分页查询套餐
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());

        Page<SetmealVO> page = setMealDishMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }
    //根据id查询套餐信息
    public SetmealVO getSetmealById(Long setmealId){
        //根据id查询套餐数据
        Setmeal setmeal = setMealDishMapper.selectById(setmealId);
        //根据套餐id查询套餐套餐菜品
        List<SetmealDish> dishes = setMealDishMapper.getDishBySetmealId(setmealId);
        //封装完整返回对象
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(dishes);
        return setmealVO;
    }

    //修改套餐
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO){
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //更新套餐信息
        setMealDishMapper.update(setmeal);
        //删除对应套餐所关联的菜品
        setMealDishMapper.deleteBySetmealId(setmealDTO.getId());
        //增添对应菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealDTO.getId());
        });
        //插入套餐菜品关系
        setMealDishMapper.insertBatch(setmealDishes);
    }
    //批量删除套餐
    @Transactional
    public void deleteSetmeal(List<Long> setmealIds){
        //判断套餐是否在售
        setmealIds.forEach(setmealId -> {
            Setmeal setmeal = setMealDishMapper.selectById(setmealId);
            if(setmeal.getStatus() == StatusConstant.ENABLE){
                //套餐在售卖中不可删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });
        //删除对应套餐信息
        setMealDishMapper.deleteById(setmealIds);
        //删除套餐所对应的菜品信息
        setMealDishMapper.deleteBatch(setmealIds);
    }
    //起售停售套餐
    public void startOrStop(Integer status, Long setmealId){
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmeal.setId(setmealId);
        setMealDishMapper.startOrStop(setmeal);
    }
}
