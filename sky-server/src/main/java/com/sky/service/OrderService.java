package com.sky.service;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

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
}
