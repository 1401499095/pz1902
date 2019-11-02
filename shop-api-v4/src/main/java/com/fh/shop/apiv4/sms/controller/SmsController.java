package com.fh.shop.apiv4.sms.controller;


import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.sms.biz.ISmsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("sms")
@CrossOrigin("*")
public class SmsController {

    @Resource(name = "smsService")
    private ISmsService smsService;


    @GetMapping
    public ServerResponse yanzheng(String moble){
        return smsService.yanzheng(moble);
    }


}
