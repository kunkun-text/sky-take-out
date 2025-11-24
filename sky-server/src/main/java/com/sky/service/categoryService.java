package com.sky.service;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

@Service
public interface categoryService {

    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);
}
