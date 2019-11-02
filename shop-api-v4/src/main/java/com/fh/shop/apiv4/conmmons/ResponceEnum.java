package com.fh.shop.apiv4.conmmons;

public enum ResponceEnum {

     USERNAME_PASSWORD_IS_NULL(1000,"用户名或密码为空"),
     USERNAME_IS_ERROR(1001,"用户名错误"),
     PWD_IS_ERROR(1003,"密码错误"),
     PASSWORD_IS_ERROR(1002,"密码连续错误三次，请第二天再次登录"),
     ALL_PASSWORD_IS_NULL(1004,"密码为空"),
     OLDPASSWORD_IS_ERROR(1005,"旧密码有误，请重新输入！"),
     NEW_OR_CONFIRM_PASSWORD_IS_ERROR(1006,"新密码确认密码不一致，请重新输入！"),
     EMAIL_NULL(1007,"邮箱不存在！"),
    PHPONE_IS_NULL(1008,"手机号为空"),
    PHPONE_IS_CZ(1009,"手机号已被注册"),
    EMALI_IS_NULL(1010,"邮箱为空"),
    VIP_NAME_IS_NULL(1011,"用户名为空"),
    VIP_NAME_IS_CZ(1012,"用户名已被注册"),
    EMALI_IS_CZ(1013,"邮箱已被注册"),
    PHONE_IS_GS_ERROR(1014,"手机号格式错误"),
    PASSWORD_IS_NULL(1015,"密码不能为空"),
    VIPNAME_IS_NOTCZ(1016,"用户名不存在"),
    HEAD_IS_NOCZ(1017,"头部信息丢失"),
    HEAD_IS_GAI(1018,"头部信息被修改"),
    HEAD_IS_GQI(1019,"登陆超时"),
    HEAD_IS_BWZ(1020,"头部信息不完整"),
    PRODUCT_IS_NOT(1021,"该商品不存在"),
    PRODUCT_NOT_SJ(1022,"该商品未上架"),
    CART_IS_NOT(1023,"购物车为空"),
    CART_PRODUCT_NOT(1024,"购物车没有商品"),
    CODE_IS_NO(1025,"验证码不一致"),
    CFCSQQ(1026,"重复发送请求"),
    ZF_WZF(1027,"未支付"),
    PAY_ORDER_IS_NOY(1028,"支付订单不存在"),
    ZF_SUCCESS(1028,"支付成功"),
    ZF_CAOS(1029,"支付超时"),


    ;
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private ResponceEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
