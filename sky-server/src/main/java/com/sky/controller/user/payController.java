package com.sky.controller.user;


import com.sky.dto.OrdersPaymentDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.service.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "用户支付")
@RequestMapping("/user/order")
public class payController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WebSocketServer webSocketServer;

    @PutMapping("/payment")
    @ApiOperation("用户支付")
    public Result payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) {

        log.info("用户支付");
        orderService.payment(ordersPaymentDTO);
        return Result.success();

    }

}
