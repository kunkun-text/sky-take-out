package com.sky.controller.admin;

import com.sky.dto.*;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "订单管理")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService OrderService;
    //订单分页查询
    @GetMapping("/conditionSearch")
    @ApiOperation("订单分页兼条件查询")
    public Result<PageResult> PageQueryAndSearch(OrdersPageQueryDTO pageQueryDTO) {
        log.info("订单分页兼条件查询,参数:{}", pageQueryDTO);
        PageResult pageResult = OrderService.conditionSearch(pageQueryDTO);
        return Result.success(pageResult);

    }
    //查询订单详情
    @RequestMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> getDetails(@PathVariable("id") Long id) {
        log.info("查询订单详情{}",id);
         OrderVO orderVO= OrderService.orderDetails(id);
        return Result.success(orderVO);
    }

    //各个订单状态统计
    @GetMapping("statistics")
    @ApiOperation("各个订单状态统计")
    public Result<OrderStatisticsVO> statistics() {
        log.info("各个订单统计");
        OrderStatisticsVO orderStatisticsVO= OrderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    //取消订单
    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        log.info("取消订单");
        OrderService.cancel(ordersCancelDTO);
        return Result.success();
    }
    //商家拒单
    @PutMapping("/rejection")
    @ApiOperation("商家拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("商家拒单");
        OrderService.rejection(ordersRejectionDTO);
        return Result.success();
    }
    //商家接单
    @PutMapping("/confirm")
    @ApiOperation("商家接单")
    public Result confirm(@RequestBody Orders id) {
        log.info("商家接单");
        OrderService.confirm(id);
        return Result.success();
    }
    //订单派送
    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result delivery(@PathVariable("id") Long id) {

        log.info("派送订单");
        OrderService.delivery(id);
        return Result.success();

    }
    //完成订单
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result complete(@PathVariable("id") Long id) {
        log.info("完成订单");
        OrderService.complete(id);
        return Result.success();
    }


}
