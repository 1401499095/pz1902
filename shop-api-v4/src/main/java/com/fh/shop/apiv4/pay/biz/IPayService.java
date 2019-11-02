package com.fh.shop.apiv4.pay.biz;

import com.fh.shop.apiv4.conmmons.ServerResponse;


public interface IPayService {

    ServerResponse createNative(Long vipId);

    ServerResponse queryPay(Long vipId);
}
