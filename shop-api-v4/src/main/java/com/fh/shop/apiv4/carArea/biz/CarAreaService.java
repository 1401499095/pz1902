package com.fh.shop.apiv4.carArea.biz;


import com.fh.shop.apiv4.carArea.po.CarArea;
import com.fh.shop.apiv4.conmmons.ServerResponse;

public interface CarAreaService {
    ServerResponse addCarArea(CarArea carArea);

    ServerResponse queryArea(Long vipId);
}
