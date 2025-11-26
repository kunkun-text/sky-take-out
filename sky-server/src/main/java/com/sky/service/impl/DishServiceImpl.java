package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.dishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private dishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    //新增菜品及口味
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO){

        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO,dish);

        //向菜品表插入数据
        dishMapper.insert(dish);
        //获取insert生成的主键值
        Long dishId=dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors !=null && flavors.size()>0){

            flavors.forEach(dishflavor->{
                dishflavor.setDishId(dishId);
            });

            //向口味表插入n条数据
            dishFlavorMapper.insertBranch(flavors);
        }
    }

    //菜品分页查询
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //菜品批量删除
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //判断当前菜品是否存在起售中-不可删除
        ids.forEach(id->{
            Dish dish = dishMapper.selectById(id);
            if(dish.getStatus() == StatusConstant.ENABLE){
                //当前菜品处于起售中
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });

        //判断菜品是否被套餐关联
        List<Long> setmealIds = setMealDishMapper.getSetmealIdByDishId(ids);
        if(setmealIds != null && setmealIds.size()>0){
            //有对应菜单不允许删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品
//        ids.forEach(id->{
//            dishMapper.deleteById(id);
//            //删除菜品关联口味数据
//            dishFlavorMapper.deleteByDishId(id);
//        });

        //根据菜品id集合批量删除菜品数据
        dishMapper.deleteByIds(ids);
        //根据菜品id集合批量删除口味数据
        dishFlavorMapper.deleteByDishIds(ids);
    }
}
