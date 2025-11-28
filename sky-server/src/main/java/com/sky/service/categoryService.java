package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface categoryService {

    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    void add(CategoryDTO categoryDTO);

    void startOrStop(Integer status, Long id);

    void delete(Integer id);

    void update(CategoryDTO categoryDTO);

    List<Category> list(Integer type);


}
