package com.fh.shop.apiv4.toKen.controller;


import com.fh.shop.apiv4.annotation.Check;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.toKen.biz.IToKenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
    @RequestMapping("/token")
public class ToKenController {

     @Resource(name="tokenService")
     private IToKenService iToKenService;


    /**
     * 解决用户重放
     * 生成UUID放入redis缓存中
     * @return
     */
    @GetMapping(value = "getToken")
    @Check
    public ServerResponse getToken(){
        return iToKenService.getTokenUUID();
    }

}
