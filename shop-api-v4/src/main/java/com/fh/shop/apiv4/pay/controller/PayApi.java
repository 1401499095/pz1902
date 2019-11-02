package com.fh.shop.apiv4.pay.controller;


import com.fh.shop.apiv4.annotation.Check;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.pay.biz.IPayService;
import com.fh.shop.apiv4.vip.vo.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pay")
public class PayApi {

    @Resource(name = "payService")
    private IPayService payService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("createNative")
    @Check
    public ServerResponse createNative(){
        VipVo attribute =(VipVo)request.getAttribute("vipVo");
        Long vipId = attribute.getId();
        return payService.createNative(vipId);
    }


    @GetMapping("queryPay")
    @Check
    public ServerResponse queryPay(){
        VipVo attribute =(VipVo)request.getAttribute("vipVo");
        Long vipId = attribute.getId();
        return payService.queryPay(vipId);

    }

}
