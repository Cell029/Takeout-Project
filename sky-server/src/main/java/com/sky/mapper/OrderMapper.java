package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderMapper {

    void insert(Orders order);

    // 根据订单号查询订单
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    // 修改订单信息
    void update(Orders orders);

    Page<Orders> pageQuery(Orders orders);

    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    Page<Orders> pageQuery2(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer toBeConfirmed);

    @Select("select * from orders where status = #{pendingPayment} and order_time < #{time}")
    List<Orders> getByStatusAndOrdertimeLT(@Param("pendingPayment") Integer pendingPayment, @Param("time") LocalDateTime time);

    Double sumByMap(Map map);

    Integer countByMap(Map map);

    List<GoodsSalesDTO> getSalesTop10(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);
}
