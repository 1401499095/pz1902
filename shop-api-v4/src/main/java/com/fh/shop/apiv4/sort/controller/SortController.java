package com.fh.shop.apiv4.sort.controller;


import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.sort.biz.ISortService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sort")
public class SortController {

    @Resource(name = "sortService")
    private ISortService sortService;


    @RequestMapping("sortList")
    public ServerResponse sortList(){
        //获取数据
        ServerResponse sortList = sortService.sortList();
        /* //将数据转换成MappingJacksonValue的数据格式
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(sortList);
        //设置函数为前台传过来的callback
        mappingJacksonValue.setJsonpFunction(callback);*/
        //响应给前台
        return sortList;
    }

}
