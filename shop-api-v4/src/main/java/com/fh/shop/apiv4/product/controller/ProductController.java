package com.fh.shop.apiv4.product.controller;


import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.product.biz.IProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource(name="productService")
    private IProductService productService;


    @PostMapping("productList")
    public ServerResponse productList(){
        //获取数据
        ServerResponse productList = productService.productList();
      /*  //将数据转换成MappingJacksonValue的数据格式
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(productList);
        //设置函数为前台传过来的callback
        mappingJacksonValue.setJsonpFunction(callback);*/
        //响应给前台
        return productList;
    }

   /* //post测试
    @RequestMapping(method = RequestMethod.GET)
    public ServerResponse findList(){
        List<Product> list =  productService.findList();
        return ServerResponse.success(list);
    }

    //新增
    @RequestMapping(method = RequestMethod.POST)
    public  ServerResponse add(Product product){
        productService.add(product);
        return ServerResponse.success();
    }
    //删除
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ServerResponse del(@PathVariable Long id){
        productService.del(id);
        return ServerResponse.success();
    }*/


}