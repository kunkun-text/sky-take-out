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
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    //根据id查询菜品信息
    @Select("select * from dish where id = #{id}")
    Dish selectById(Long id);

    //删除对应id菜品
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);
    //根据菜品id集合批量删除菜品
    void deleteByIds(List<Long> ids);
    //修改菜品id
    @AutoFIll(value = OperationType.UPDATE)
    void update(Dish dish);
    //起售或停售对应菜品
    @AutoFIll(value = OperationType.UPDATE)
    @Update("update dish set status = #{status} where id = #{id}")
    void startOrStop(Dish dish);
}
