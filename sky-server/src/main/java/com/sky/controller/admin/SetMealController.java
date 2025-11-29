package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
@Slf4j
public class SetMealController {

    @Autowired
    SetMealService setMealService;

    //新增套餐
    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐信息为:{}",setmealDTO);
        setMealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    //分页查询套餐
    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("所传入的分页查询参数为：{}",setmealPageQueryDTO);
        PageResult pageResult = setMealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    //根据id查询套餐
    @GetMapping("{id}")
    @ApiOperation("根据id查询套餐")
    public Result getSetmealById(@PathVariable("id") Long setmealId) {
        log.info("根据id查询套餐信息为:{}",setmealId);
        SetmealVO setmealVO = setMealService.getSetmealById(setmealId);
        log.info("根据id查询套餐信息为:{}",setmealVO);
        return Result.success(setmealVO);
    }


    //修改套餐
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐信息为:{}",setmealDTO);
        setMealService.updateSetmeal(setmealDTO);
        return Result.success();
    }
    //删除套餐
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteSetmeal(@RequestParam("ids") List<Long> setmealIds) {
        log.info("要删除的套餐序列为：{}",setmealIds);
        setMealService.deleteSetmeal(setmealIds);
        return Result.success();
    }

    //起售/停售套餐
    @PostMapping("/status/{status}")
    @ApiOperation("起售停售套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable("status") Integer status,@RequestParam Long id) {
        log.info("要设置的套餐id为:{}",id);
        setMealService.startOrStop(status,id);
        return Result.success();

    }


}
