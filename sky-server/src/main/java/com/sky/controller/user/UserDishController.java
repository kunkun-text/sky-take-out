package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Api("根据分类id查询菜品")
@RequestMapping("/user/dish")
@Slf4j
public class UserDishController {

    @Autowired
    private DishService dishService;

    @RequestMapping("/list")
    public Result<List<DishVO>> selectById(@RequestParam Long categoryId) {
        log.info("要查询分类id为{}的菜品及口味",categoryId);
        List<DishVO> list = dishService.getByCategoryIdWithFlavor(categoryId);
        return Result.success(list);

    }

}
