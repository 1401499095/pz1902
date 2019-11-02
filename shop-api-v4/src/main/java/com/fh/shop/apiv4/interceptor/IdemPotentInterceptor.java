package com.fh.shop.apiv4.interceptor;


import com.fh.shop.apiv4.annotation.ApiIdemPotent;
import com.fh.shop.apiv4.conmmons.ResponceEnum;
import com.fh.shop.apiv4.exception.GlobalException;
import com.fh.shop.apiv4.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdemPotentInterceptor extends HandlerInterceptorAdapter {
    /**
     * 解决用户重复登录
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if(!method.isAnnotationPresent(ApiIdemPotent.class)){
                 return true;
        }

        String token = request.getHeader("token");
         if(StringUtils.isEmpty(token)){
             throw new GlobalException(ResponceEnum.HEAD_IS_NOCZ);
         }

        boolean exist = RedisUtil.exist(token);
          if(!exist){
              throw new GlobalException(ResponceEnum.HEAD_IS_NOCZ);
          }

        long delCountToken = RedisUtil.delCount(token);
            if(delCountToken<=0){
                throw new GlobalException(ResponceEnum.CFCSQQ);
            }

        return true;
    }

}
