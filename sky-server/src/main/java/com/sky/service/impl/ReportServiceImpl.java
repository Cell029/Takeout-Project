package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
        // 当前集合存放从 begin 到 end 范围内的每天的日期
        List<LocalDate> dates = new ArrayList<>();
        dates.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
            dates.add(begin);
        }
        // 查询对应每天的营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dates) {
            // 因为 order 表中的下单时间格式为 年月日 时分秒，所以也需要按照这样的格式去查询
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN); // 获取当天的最早时间 00:00:00
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX); // 获取当天最晚时间 23:59:59
            Map map = new HashMap();
            // 统计营业额需要查询订单状态为已完成的
            map.put("status", Orders.COMPLETED);
            map.put("begin",beginTime);
            map.put("end", endTime);
            // select sum(amount) from orders where order_time > ? and order_time < ? and status = 5
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dates,","))
                .turnoverList(StringUtils.join(turnoverList,","))
                .build();
    }
}
