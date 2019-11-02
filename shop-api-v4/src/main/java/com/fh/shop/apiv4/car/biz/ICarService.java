package com.fh.shop.apiv4.car.biz;

import com.fh.shop.apiv4.conmmons.ServerResponse;

public interface ICarService {
    ServerResponse addCar(Long productId, Long count, Long vipId);

    ServerResponse findCart(Long vipId);

    ServerResponse deleteCart(Long userVipVoId, Long productId);

}
