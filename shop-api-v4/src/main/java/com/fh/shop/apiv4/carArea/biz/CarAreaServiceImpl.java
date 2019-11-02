package com.fh.shop.apiv4.carArea.biz;


import com.fh.shop.apiv4.carArea.mapper.CarAreaMapper;
import com.fh.shop.apiv4.carArea.po.CarArea;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("carAreaService")
public class CarAreaServiceImpl implements CarAreaService{

    @Autowired
    private CarAreaMapper carAreaMapper;


    @Override
    public ServerResponse addCarArea(CarArea carArea) {
        carAreaMapper.insert(carArea);
        return ServerResponse.success();
    }

    //根据用户id查地区
    @Override
    public ServerResponse queryArea(Long vipId) {
        List<CarArea> List = carAreaMapper.queryArea(vipId);
        return ServerResponse.success(List);
    }
}
