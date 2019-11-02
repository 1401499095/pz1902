package com.fh.shop.apiv4.brand.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class aas {

    @Scheduled(cron = " */5 * * * * ?")
    public void sss(){
    }
}
