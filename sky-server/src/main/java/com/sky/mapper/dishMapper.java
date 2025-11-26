package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFIll;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface dishMapper {

    //根据id查询菜品数量
    @Select("select count(id) from dish where category_id = #{id}")
    Integer CountById(Integer id);

    //插入菜品数据
    @AutoFIll(value = OperationType.INSERT)
    void insert(Dish dish);
    //菜品分页查询
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id = #{id}")
    Dish selectById(Long id);
    //删除对应id菜品
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);
}
