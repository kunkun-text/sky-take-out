package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.*;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    //待确认订单数
    @Select("select count(*) from orders where status = 2")
    Integer getToBeConfirmed();
    //等待派送订单数量
    @Select("select count(*) from orders where status = 3")
    Integer getConfirmed();
    //正在派送数量
    @Select("select count(*) from orders where status = 4")
    Integer getDelivery();
    //商家取消订单
    @Update("update orders set status = 6, cancel_reason = #{cancelReason},cancel_time = now(),pay_status = 2 where id = #{id}")
    void cancel(OrdersCancelDTO ordersCancelDTO);
    //商家拒单
    @Update("update orders set status = 6, reejection_reason = #{rejectionReason},cancel_time = now(),pay_status = 2 where id = #{id}")
    void reject(OrdersRejectionDTO ordersRejectionDTO);
    //退款状态更新
    @Update("update orders set status = 6, pay_status = 2 where id = #{id}")
    void payBack(Long id);
    //商家接单
    @Update("update orders set status = 3 where id = #{id}")
    void confirm(Orders id);
    //派送订单
    @Update("update orders set status = 4 where id = #{id}")
    void delivery(Long id);
    //完成订单
    @Update("update orders set status = 5, delivery_time = now() where id = #{id}")
    void complete(Long id);
    //查询订单状态位未付款并且时间超过15分钟订单
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);
    //模拟用户支付
    @Update("update orders set pay_status = 1, status = 2, checkout_time = now() where number = #{orderNumber}")
    void payment(OrdersPaymentDTO ordersPaymentDTO);
    //根据订单号查询订单
    @Select("select * from orders where number = #{orderNumber}")
    Orders selectByNumber(OrdersPaymentDTO ordersPaymentDTO);
    //根据时间区间查询营业额
    Double sumByMap(Map map);
    //查询状态为已付款或已退款订单
    Integer getAllOrders(Map map);
    //退款
    @Update("update orders set pay_status = 2")
    void payback(OrdersCancelDTO ordersCancelDTO);
    //查询已完成订单数量
    Integer getRightOrders(Map map);
    //查询对应日期菜品销量前十
    List<GoodsSalesDTO> getTop10(LocalDateTime beginTime,LocalDateTime  endTime);
}
