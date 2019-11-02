package com.fh.shop.apiv4.car.controller;


import com.fh.shop.apiv4.annotation.Check;
import com.fh.shop.apiv4.car.biz.ICarService;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.vip.vo.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/car")
public class CartController {
      @Resource(name="cartservice")
      private ICarService iCarService;
       @Autowired
       private HttpServletRequest request;

       //添加到购物车
       @PostMapping(value = "addCar")
       @Check
       public ServerResponse addCar(Long productId, Long count){
           VipVo attribute =(VipVo) request.getAttribute("vipVo");
           Long vipId = attribute.getId();
           return iCarService.addCar(productId,count,vipId);
       }

       //查询购物车
        @GetMapping(value = "quertCar")
        @Check
        public ServerResponse findCart(){
           VipVo attribute =(VipVo)request.getAttribute("vipVo");
           Long vipId = attribute.getId();
           return iCarService.findCart(vipId);
       }

       //删除购物车
       @DeleteMapping("{productId}")
       @Check
        public ServerResponse deleteCart(@PathVariable Long productId){
            VipVo attribute =(VipVo)request.getAttribute("vipVo");
            Long userVipVoId = attribute.getId();
            return iCarService.deleteCart(userVipVoId,productId);
        }

}
