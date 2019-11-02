package com.fh.shop.apiv4.toKen.biz;


import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("tokenService")
public class ToKenServiceImpl implements IToKenService {


    @Override
    public ServerResponse getTokenUUID() {
        String token = UUID.randomUUID().toString();
        //将生成的UUID放入redis缓存中
        RedisUtil.set(token,token);
        return ServerResponse.success(token);
    }
}
