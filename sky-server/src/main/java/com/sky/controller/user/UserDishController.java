package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Api(tags = "根据分类id查询菜品")
@RequestMapping("/user/dish")
@Slf4j
public class UserDishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/list")
    @ApiOperation("根据分类查询菜品及口味")
    public Result<List<DishVO>> selectById(@RequestParam Long categoryId) {
        log.info("要查询分类id为{}的菜品及口味",categoryId);

        //定义redis的key
        String key = "dish_" +categoryId;

        //查询redis缓存，看是否存在分类菜品数据
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        //判断如果存在直接返回
        if(list != null && list.size() >0) {
            return Result.success(list);
        }
        else {
            //不存在，查询数据库并存入redis中
            list = dishService.getByCategoryIdWithFlavor(categoryId);
            redisTemplate.opsForValue().set(key, list);
            return Result.success(list);
        }
    }

}
