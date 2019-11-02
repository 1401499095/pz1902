package com.fh.shop.apiv4.exception;


import com.fh.shop.apiv4.conmmons.ResponceEnum;

public class GlobalException extends RuntimeException{

    private ResponceEnum responceEnum;

    public GlobalException(ResponceEnum responceEnum){
        this.responceEnum = responceEnum;
    }

    public ResponceEnum getResponceEnum(){
        return this.responceEnum;
    }

}
