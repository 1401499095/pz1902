package com.fh.shop.apiv4.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.apiv4.order.po.Order;

import java.util.List;

public interface IOrderMapper extends BaseMapper<Order> {

    List<String> selectOutTimeOrder();
}
