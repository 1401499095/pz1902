package com.fh.shop.apiv4.product.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.product.mapper.IProductMapper;
import com.fh.shop.apiv4.product.po.Product;
import com.fh.shop.apiv4.util.MailUtil;
import com.fh.shop.apiv4.util.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpringProduct {

    @Autowired
    private IProductMapper productMapper;

    @Scheduled(cron = " */5 * * * * ?")
    public ServerResponse queryProductCount(){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("null",null);
        List<Product> products = productMapper.selectList(queryWrapper);
        if (products !=null){
            Integer stock =null;
            String productName =null;
            Long id = null;
            List<Product> arr = new ArrayList<>();
            for (Product product : products) {
                if (product.getStock()<=5){
                    arr.add(product);
                }
            }
            for (Product product : arr) {
                 stock = product.getStock();
                 productName = product.getProductName();
                 id = product.getId();
            }
            //发邮件
            MailUtil.sendMail("温馨提示","商品id为"+id+"的"+productName+"库存为"+stock,SystemConst.FA_EMAIL);
        }
        return ServerResponse.success();
    }
}
