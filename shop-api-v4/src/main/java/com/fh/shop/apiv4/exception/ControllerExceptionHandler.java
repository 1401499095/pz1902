package com.fh.shop.apiv4.exception;

import com.fh.shop.apiv4.conmmons.ResponceEnum;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice

public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public ServerResponse handlerGlobalException(GlobalException globalException){
        ResponceEnum responceEnum = globalException.getResponceEnum();
        return ServerResponse.error(responceEnum);
    }
}
