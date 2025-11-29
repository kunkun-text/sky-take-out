package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车模块")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    //添加购物车
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){

        log.info("添加商品信息：{}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    //查看购物车
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> list(){
        log.info("查看购物车");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }
    //删除购物车一个商品
    @PostMapping("/sub")
    @ApiOperation("删除购物车一个商品")
    public Result subShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("删除购物车一个商品：{}",shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);
        shoppingCartService.list();
        return Result.success();
    }
    //清空购物车
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result cleanShoppingCart(){
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }

}
