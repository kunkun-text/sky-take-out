package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.categoryMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.categoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //新增菜品分类
    @PostMapping
    @ApiOperation("新增分类")
    public Result add(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增菜品分类{}", categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }

    //启用禁用菜品分类
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用菜品分类")
    public Result startOrStop(@PathVariable("status") Integer status,Long id) {
        log.info("启用或禁用菜品分类信息状态:{},id:{}:", status,id);
        categoryService.startOrStop(status,id);
        return Result.success();

    }

    //删除对应id菜品分类
    @DeleteMapping
    @ApiOperation("删除菜品分类")
    public Result delete(@RequestParam Integer id) {
        log.info("删除菜品id为{}",id);
        categoryService.delete(id);
        return Result.success();
    }


    //修改对应菜品分类
    @PutMapping
    @ApiOperation("修改对应id菜品分类")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改的数据为{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }



}
