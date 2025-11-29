package com.sky.controller.user;


import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.categoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("user")
@RequestMapping("/user/category")
@Api(tags = "用户端查询菜品分类")
@Slf4j
public class UserCategoryController {


    @Autowired
    categoryService categoryService;

    //根据type查询分类
    @GetMapping("/list")
    @ApiOperation("用户端查询菜品分类")
    public Result<List<Category>> selectCategory(Integer type) {

            log.info("传入的状态为{}", type);
            List<Category> list = categoryService.list(type);
            return Result.success(list);
    }


}
