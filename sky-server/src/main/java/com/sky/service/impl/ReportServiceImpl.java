package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserMapper userMapper;


    /**
     * 营业额统计
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end) {

        //存放日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        while(!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        //存放营业额
        List<Double> turnoverList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            //查询对应日期营业额
            Map map = new HashMap();
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnoverList.add(turnover == null ? 0.0 : turnover);

        }

        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .turnoverList(StringUtils.join(turnoverList,","))
                .build();
    }
     /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO getUserReport(LocalDate begin, LocalDate end) {

        //日期统计
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        //用户统计
        List<Integer> totalUserList = new ArrayList<>();
        //新增用户统计
        List<Integer> newUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            //查询对应日期用户数量
            Map map = new HashMap();
            map.put("endTime", endTime);
            //总用户数量
            Integer userCount = userMapper.countByMap(map);
            map.put("beginTime", beginTime);
            //新增用户数量
            Integer newUserCount = userMapper.countByMap(map);
            newUserList.add(newUserCount == null ? 0 : newUserCount);
            totalUserList.add(userCount == null ? 0 : userCount);
        }


        return UserReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .build();
    }

    //订单统计
    public OrderReportVO orderCountReport(LocalDate begin, LocalDate end) {

        //时间列表
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }


        List<Integer> countList = new ArrayList<>();
        List<Integer> RightList = new ArrayList<>();

        int countAllOrders = 0;
        int rightAllOrders = 0;
        double orderCompletionRate =0.0;

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap();
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
            //订单数列表和有效订单数列表
            Integer totalOrders = orderMapper.getAllOrders(map);
            //总订单数
            countAllOrders += totalOrders == null ? 0 : totalOrders;

            countList.add(totalOrders == null ? 0 : totalOrders);

            Integer rightOrders = orderMapper.getRightOrders(map);
            RightList.add(rightOrders == null ? 0 : rightOrders);
            //有效订单数
            rightAllOrders += rightOrders == null ? 0 : rightOrders;

        }

        //订单完成率
        orderCompletionRate = (double) rightAllOrders / countAllOrders;


        //返回
        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .orderCountList(StringUtils.join(countList,","))
                .validOrderCountList(StringUtils.join(RightList,","))
                .totalOrderCount(countAllOrders)
                .validOrderCount(rightAllOrders)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    //菜品前十统计
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {

            LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

            List<GoodsSalesDTO> listTop10 = orderMapper.getTop10(beginTime,endTime);

            List<String> names = listTop10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
            String nameList =StringUtils.join(names,",");

            List<Integer> numbers = listTop10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
            String numberList =StringUtils.join(numbers,",");
        //销量表
        return SalesTop10ReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();

    }

}
