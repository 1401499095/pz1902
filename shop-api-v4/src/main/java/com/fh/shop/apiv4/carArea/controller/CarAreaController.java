package com.fh.shop.apiv4.carArea.controller;


import com.fh.shop.apiv4.annotation.Check;
import com.fh.shop.apiv4.carArea.biz.CarAreaService;
import com.fh.shop.apiv4.carArea.po.CarArea;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.vip.vo.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("carArea")
@CrossOrigin
public class CarAreaController {

    @Resource(name = "carAreaService")
    private CarAreaService carAreaService;
    @Autowired
    private HttpServletRequest request;

    //新增收货地址
    @GetMapping(value = "addCarArea")
    @Check
    public ServerResponse addCarArea(CarArea carArea){
        VipVo attribute =(VipVo)request.getAttribute("vipVo");
        Long vipId = attribute.getId();
        carArea.setUserId(vipId);
        return carAreaService.addCarArea(carArea);
    }

    //查询默认的收货地址
    @PostMapping(value = "queryArea")
    @Check
    public ServerResponse queryArea(){
        VipVo attribute =(VipVo)request.getAttribute("vipVo");
        Long vipId = attribute.getId();
        return carAreaService.queryArea(vipId);
    }


}
