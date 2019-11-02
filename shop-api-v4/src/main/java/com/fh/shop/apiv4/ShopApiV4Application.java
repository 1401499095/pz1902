package com.fh.shop.apiv4;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fh.shop.apiv4.*.mapper")
public class ShopApiV4Application {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiV4Application.class, args);
    }

}
