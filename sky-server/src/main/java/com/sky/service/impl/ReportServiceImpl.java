package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WorkspaceService workspaceService;

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

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        // 讲开始日期到结束日期的所有日期添加到集合中
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Integer> newUserList = new ArrayList<>(); // 新增用户数
        List<Integer> totalUserList = new ArrayList<>(); // 总用户数
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            // 新增用户数量 select count(id) from user where create_time > ? and create_time < ?
            Integer newUser = getUserCount(beginTime, endTime);
            // 总用户数量截止目前日期的所有用户量，所以可以不用考虑起始日期
            // select count(id) from user where  create_time < ?
            Integer totalUser = getUserCount(null, endTime);
            newUserList.add(newUser);
            totalUserList.add(totalUser);
        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .build();
    }

    @Override
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(begin);
        while (!begin.equals(end)){
            begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
            dates.add(begin);
        }
        // 每日订单总数集合
        List<Integer> orderCountList = new ArrayList<>();
        // 每日有效订单数集合
        List<Integer> validOrderCountList = new ArrayList<>();
        // 时间区间内的总订单数
        Integer totalOrderCount = 0;
        // 时间区间内的总有效订单数
        Integer validTotalOrderCount = 0;
        // 订单完成率
        Double orderCompletionRate = 0.0;
        for (LocalDate date : dates) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            // 查询每天的总订单数 select count(id) from orders where order_time > ? and order_time < ?
            Integer orderCount = getOrderCount(beginTime, endTime, null);
            // 查询每天的有效订单数 select count(id) from orders where order_time > ? and order_time < ? and status = ?
            Integer validOrderCount = getOrderCount(beginTime, endTime, Orders.COMPLETED);
            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
            totalOrderCount += orderCount;
            validTotalOrderCount += validOrderCount;
        }
        if(totalOrderCount != 0){
            orderCompletionRate = validTotalOrderCount.doubleValue() / totalOrderCount;
        }
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dates, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validTotalOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);
        List<String> nameList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();
        for (GoodsSalesDTO goodsSalesDTO : goodsSalesDTOList) {
            nameList.add(goodsSalesDTO.getName());
            numberList.add(goodsSalesDTO.getNumber());
        }

        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(numberList.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")))
                .build();
    }

    @Override
    public void exportBusinessData(HttpServletResponse response) {
        // 获取当前系统日期向前推 30 天
        LocalDate begin = LocalDate.now().minusDays(30);
        // 获取当前系统日期向前推 1 天
        LocalDate end = LocalDate.now().minusDays(1);
        // 查询概览运营数据（工作台获取的概览数据），提供给 Excel 模板文件
        BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin,LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
        // 通过输入流读取模板文件
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            // 基于提供好的模板文件创建一个新的 Excel 表格对象
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            // 获得 Excel 文件中的一个 Sheet 页
            XSSFSheet sheet = excel.getSheet("Sheet1");
            // 将日期填充到 Excel 文件的第二行的第二个单元格
            sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);
            // 获得第 4 行
            XSSFRow row = sheet.getRow(3);
            // 获取单元格
            // 第三个单元格填充营业额
            row.getCell(2).setCellValue(businessData.getTurnover());
            // 第五个单元格填充订单完成率
            row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
            // 第七个单元格填充新增用户
            row.getCell(6).setCellValue(businessData.getNewUsers());
            // 获取第五行
            row = sheet.getRow(4);
            // 第三个单元格填充有效订单
            row.getCell(2).setCellValue(businessData.getValidOrderCount());
            // 第五个单元格填充平均单价
            row.getCell(4).setCellValue(businessData.getUnitPrice());
            // 获取一个月中每天的详细营业数据
            for (int i = 0; i < 30; i++) {
                // 循环遍历每个日期，每次加一天
                LocalDate date = begin.plusDays(i);
                // 获取当日的详细数据
                businessData = workspaceService.getBusinessData(LocalDateTime.of(date,LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
                // 初始数据从第八行开始填充，下一天的数据填充到下一行
                row = sheet.getRow(7 + i);
                // 第二个单元格填充日期
                row.getCell(1).setCellValue(date.toString());
                // 第三个单元格填充当日营业额
                row.getCell(2).setCellValue(businessData.getTurnover());
                // 第四个单元格填充当日有效订单
                row.getCell(3).setCellValue(businessData.getValidOrderCount());
                // 第五个单元格填充当日订单完成率
                row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
                // 第六个单元格填充当日平均单价
                row.getCell(5).setCellValue(businessData.getUnitPrice());
                // 第七个单元格填充新增用户
                row.getCell(6).setCellValue(businessData.getNewUsers());
            }
            // 获取当前 HTTP 响应的输出流
            ServletOutputStream out = response.getOutputStream();
            // 将内存中的 Excel 文件内容写入 ServletOutputStream 输出流，也就是写入 HTTP 响应体中
            excel.write(out);
            // 关闭资源
            out.flush();
            out.close();
            excel.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Integer getOrderCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status) {
        Map map = new HashMap();
        map.put("begin", beginTime);
        map.put("end", endTime);
        map.put("status", status);
        return orderMapper.countByMap(map);
    }

    private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
        Map map = new HashMap();
        map.put("begin",beginTime);
        map.put("end", endTime);
        return userMapper.countByMap(map);
    }
}
