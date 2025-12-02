package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    //营业额统计
    TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end);
    //用户统计
    UserReportVO getUserReport(LocalDate begin, LocalDate end);
    //订单统计
    OrderReportVO orderCountReport(LocalDate begin, LocalDate end);
    //top10统计
    SalesTop10ReportVO top10(LocalDate begin, LocalDate end);
}

