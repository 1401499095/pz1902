package com.fh.shop.apiv4.order.controller;


import com.fh.shop.apiv4.annotation.ApiIdemPotent;
import com.fh.shop.apiv4.annotation.Check;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.order.biz.IOrderService;
import com.fh.shop.apiv4.order.param.OrderParam;
import com.fh.shop.apiv4.vip.vo.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderApi {
    @Resource(name = "orderService")
    private IOrderService orderService;

    @Autowired
    private HttpServletRequest request;

    //添加订单
    @Check
    @PostMapping
    @ApiIdemPotent
    public ServerResponse addOrder(OrderParam orderParam) {
        VipVo attribute =(VipVo)request.getAttribute("vipVo");
        Long vipId = attribute.getId();
        return orderService.addOrder(vipId,orderParam);
    }

    //关闭订单
    @Scheduled(cron = "0/30 * * * * ?")
    public void closeOrder(){
        List<String> list= orderService.selectOutTimeOrder();
        System.out.println(123);
        if (list.size()>0){
            orderService.deleteOrderBatch(list);
        }
    }

}
