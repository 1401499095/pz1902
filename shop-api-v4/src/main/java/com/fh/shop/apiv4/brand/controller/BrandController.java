package com.fh.shop.apiv4.brand.controller;


import com.fh.shop.apiv4.annotation.Check;
import com.fh.shop.apiv4.brand.biz.IBrandService;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Resource(name="brandService")
    private IBrandService brandService;

    @PostMapping("brandList")
    @Check
    public ServerResponse brandList(){
        //获取数据
        ServerResponse brandList = brandService.brandList();
       /* //将数据转换成MappingJacksonValue的数据格式
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(brandList);
        //设置函数为前台传过来的callback
        mappingJacksonValue.setJsonpFunction(callback);*/
        //响应给前台
        return brandList;
    }


   /* *//**
     * 分页 on  条件查询
     * @param
     * @return
     *//*
    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin("*")
    public DataTableResult findBrand(ParmBrand parmBrand){
     return brandService.findBrand(parmBrand);

    }

    *//**
     * 添加商品
     * @param
     * @return
     *//*
    @RequestMapping(method = RequestMethod.POST)
    @CrossOrigin("*")
    public ServerResponse addBrand(Brand brand){
        return brandService.addBrand(brand);
    }

    *//**
     * 删除
     * @param
     * @return
     *//*
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @CrossOrigin("*")
    public ServerResponse delBrand(@PathVariable Integer id){
        return brandService.delBrand(id);
    }

    *//**
     * 回显
     * @param
     * @return
     *//*
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @CrossOrigin("*")
    public ServerResponse toUpdateBrand(@PathVariable Integer id){
        return brandService.toUpdateBrand(id);
    }

    *//**
     * 修改
     * @param
     * @return
     *//*
    @RequestMapping(method = RequestMethod.PUT)
    @CrossOrigin("*")
    public ServerResponse updateBrand(@RequestBody Brand brand){
        return brandService.updateBrand(brand);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @CrossOrigin("*")
    public ServerResponse deleteQuanBrand(Integer[] ids){
        return brandService.deleteQuanBrand(ids);
    }*/

}