package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface categoryMapper {

    //分页查询菜品信息
    Page<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);
}
