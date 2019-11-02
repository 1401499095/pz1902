package com.fh.shop.apiv4.order.biz;

import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.order.param.OrderParam;

import java.util.List;

public interface IOrderService {
    ServerResponse addOrder(Long vipId, OrderParam orderParam);


    List<String> selectOutTimeOrder();

    void deleteOrderBatch(List<String> list);

}
