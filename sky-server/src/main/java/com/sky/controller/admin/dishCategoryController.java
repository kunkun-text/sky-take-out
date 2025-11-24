package com.sky.controller.admin;


import com.sky.dto.CategoryPageQueryDTO;
import com.sky.mapper.categoryMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.categoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//菜品分类管理
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "菜品分类接口")
public class dishCategoryController {

    @Autowired
    private categoryService categoryService;

    //分页查询菜品
    @GetMapping("/page")
    @ApiOperation("分页菜品查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("菜品分类参数为：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }



}
