package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.categoryMapper;
import com.sky.mapper.dishMapper;
import com.sky.mapper.setMealMapper;
import com.sky.result.PageResult;
import com.sky.service.categoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.sky.constant.StatusConstant.DISABLE;

@Service
public class categoryServicesImpl implements categoryService {

    @Autowired
    categoryMapper categoryMapper;

    @Autowired
    dishMapper dishMapper;

    @Autowired
    setMealMapper setMealMapper;

    //分页查询菜品
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO){
        //开始分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.page(categoryPageQueryDTO);

        long total = page.getTotal();
        List<Category> records = page.getResult();

        return new PageResult(total,records);

    }
    //新增菜品分类
    public void add(CategoryDTO categoryDTO){
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //补充菜品信息
        category.setStatus(DISABLE);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }

    //启用或禁用菜品分类
    public void startOrStop(Integer status, Long id){

        Category category = new Category();
        category.setStatus(status);
        category.setId(id);

        categoryMapper.update(category);
    }

    //删除对应id菜品分类
    public void delete(Integer id){

        //查询当前分类是否关联菜品，如果关联则抛出异常
        Integer count = dishMapper.CountById(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //查询当前分类是否关联套餐
        count = setMealMapper.CountById(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        //删除分类
        categoryMapper.delete(id);
    }

    //修改员工对应数据
    public void update(CategoryDTO categoryDTO){
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }

    //根据类型查询分类
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
