package com.fh.shop.apiv4.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.fh.shop.apiv4.annotation.Check;
import com.fh.shop.apiv4.conmmons.ResponceEnum;
import com.fh.shop.apiv4.exception.GlobalException;
import com.fh.shop.apiv4.util.KeyUtil;
import com.fh.shop.apiv4.util.Md5Util;
import com.fh.shop.apiv4.util.RedisUtil;
import com.fh.shop.apiv4.util.SystemConst;
import com.fh.shop.apiv4.vip.vo.VipVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //================================处理ajax跨域请求==========================================
        //指定客户端IP地址
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"http://localhost:8082");
        //添加头部信息
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"token");
        //允许请求动作
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"GET,DELETE,POST,PUT");

        //处理options请求
        String method1 = request.getMethod();
        if (method1.equalsIgnoreCase("options")){
            return false;
        }

        //获取方法    判断方法是否加入了注解   对有注解的方法进行拦截
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(Check.class)){
            return true;
        }

        //获取header
        String header = request.getHeader("x-auth");
        if (StringUtils.isEmpty(header)){
            throw new GlobalException(ResponceEnum.HEAD_IS_NOCZ);
        }
        //=======================验证签名==============================
        //分割
        String[] split = header.split("\\.");
        //当数组长度小于2时  数组信息不完整
        if(split.length != 2){
            throw new GlobalException(ResponceEnum.HEAD_IS_BWZ);
        }
        String BaseString = split[0];
        String BaseMd564 = split[1];

        //加密
        String sign = Md5Util.sign(BaseString, SystemConst.SECRET_KEY);
        //编译
        String base = Base64.getEncoder().encodeToString(sign.getBytes());
        //判断头部信息是否被修改
        if (!BaseMd564.equals(base)){
            throw new GlobalException(ResponceEnum.HEAD_IS_GAI);
        }

        //从只编译的字符串中获取用户信息
        String  decode = new String(Base64.getDecoder().decode(BaseString));
        VipVo vipVo = JSONObject.parseObject(decode, VipVo.class);
        String vip = vipVo.getVip();
        String uuid = vipVo.getUuid();

        //判断头信息是否过期
        boolean exist = RedisUtil.exist(KeyUtil.buildVipKey(vip, uuid));
        //判断该头信息是否过期  false代表过期 true代表未过期
        if (!exist){
            throw new GlobalException(ResponceEnum.HEAD_IS_GQI);
        }

        //执行请求后 为redis中的key续命
        RedisUtil.expire(KeyUtil.buildVipKey(vip, uuid),SystemConst.VIP_GQI);

        //将vo存放到request作用域中
        request.setAttribute("vipVo",vipVo);
        return true;
    }

}
