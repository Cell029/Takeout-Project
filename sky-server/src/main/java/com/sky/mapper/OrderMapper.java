package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Select;

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
}
