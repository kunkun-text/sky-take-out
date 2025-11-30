package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单
     * @param orders
     */
    void insert(Orders orders);

    //根据id查询订单
    @Select("select * from orders where id = #{id}")
    Orders selectById(Long id);
    //历史订单查询
    Page<Orders> select(OrdersPageQueryDTO ordersPageQueryDTO);
    //修改订单状态
    void update(Orders orders);
}
