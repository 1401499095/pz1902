package com.fh.shop.apiv4.sms.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.apiv4.conmmons.ResponceEnum;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.util.RedisUtil;
import com.fh.shop.apiv4.util.SendUtil;
import com.fh.shop.apiv4.util.SystemConst;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service("smsService")
public class ISmsServiceImpl implements ISmsService{


    @Override
    public ServerResponse yanzheng(String moble) {
        //验证手机号
        if (moble == null){
            //返回手机号为空
            return ServerResponse.error(ResponceEnum.PHPONE_IS_NULL);
        }

        //===============正则验证手机号格式是否正确======================
        //java手机号正则表达式
        String pattern = "";//^1(3|5|7|8)\d{9}   //java手机号正则
        //传入手机号和正则验证 返回 true 和 false
        //true代表手机格式正确   false代表手机格式错误
        boolean isMatch = Pattern.matches(pattern, moble);

        if (isMatch == false){
            //手机格式不对  返回前台提示信息   手机格式不正确
            return ServerResponse.error(ResponceEnum.PHONE_IS_GS_ERROR);
        }
        //===================================================================
        //获取验证码
        String code = SendUtil.getCode(moble);
        //将验证码转换成json格式
        String JsonCode = JSONObject.toJSONString(code);
        //存放到redis中
        RedisUtil.setex("code"+moble,SystemConst.SMSCODE_IS_NULL,JsonCode);

        return ServerResponse.success();
    }
}
