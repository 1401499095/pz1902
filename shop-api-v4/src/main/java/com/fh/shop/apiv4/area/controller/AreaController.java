package com.fh.shop.apiv4.area.controller;

import com.fh.shop.apiv4.area.biz.IAreaService;
import com.fh.shop.apiv4.area.po.Area;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/areas")
@CrossOrigin("*")
public class AreaController {

    @Resource(name = "areaService")
    private IAreaService areaService;

    @RequestMapping(method = RequestMethod.GET)
    public ServerResponse queryAreaList(Integer id){
        List<Area> list = areaService.queryAreaList(id);
        return ServerResponse.success(list);
    }

}
