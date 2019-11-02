package com.fh.shop.apiv4.vip.biz;


import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.vip.po.Vip;

public interface IVipService {

    ServerResponse add(Vip vip);

    ServerResponse queryName(String vip);

    ServerResponse queryEmali(String emali);

    ServerResponse queryPhone(String phone);

    ServerResponse login(Vip vip);

    ServerResponse vipInfo(String vip);

    ServerResponse mlogin(String phone, String code);
    ///ServerResponse mlogin(Vip vip);
}
