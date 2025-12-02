package com.sky.service;


import com.sky.dto.*;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.*;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    /**
     * 提交订单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
    //取消订单
    void cancelOrder(Long id) throws Exception;
    //查询订单详情
    OrderVO orderDetails(Long id);
    //查询历史订单
    PageResult historyOrders(OrdersPageQueryDTO ordersPageQueryDTO);
    //再来一单
    void repetition(Long id);

    //订单查询
    PageResult conditionSearch(OrdersPageQueryDTO pageQueryDTO);
    //各个订单统计
    OrderStatisticsVO statistics();
    //商家取消订单
    void cancel(OrdersCancelDTO ordersCancelDTO);
    //商家拒单
    void rejection(OrdersRejectionDTO ordersRejectionDTO);
    //商家接单
    void confirm(Orders id);
    //派送订单
    void delivery(Long id);
    //完成订单
    void complete(Long id);
    //模拟用户支付
    void payment(OrdersPaymentDTO ordersPaymentDTO);
    //用户催单
    void reminder(Long id);
}
