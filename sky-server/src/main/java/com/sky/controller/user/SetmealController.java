package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/user/setmeal")
@Api("用户端套餐接口")
@Slf4j
public class SetmealController {

    @Autowired
    SetMealService setMealService;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    public Result<List<Setmeal>> listById(Integer categoryId) {
        log.info("要查询的套餐分类id为{}", categoryId);
        List<Setmeal> list= setMealService.listByCategoryId(categoryId);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询套餐包含菜品")
    public Result<List<DishItemVO>> selectByDishId(@PathVariable Long id) {
        log.info("所需要查找id为：{}的套餐菜品",id);
         List<DishItemVO> list = setMealService.getDishBySetmealId(id);
         log.info(list.toString());
         return Result.success(list);
    }


}
