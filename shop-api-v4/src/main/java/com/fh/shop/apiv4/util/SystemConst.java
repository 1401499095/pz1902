package com.fh.shop.apiv4.util;

public final class SystemConst {
    public static final String CURRENT_USER ="user";
    public static final String CURRENT_MENU ="menuList";
    public static final String MENU_URL ="url";
    public static final String All_MENU_URL ="allMenuUrl";
    public static final Integer LOG_INFO_SUCCESS =1;
    public static final Integer LOG_INFO_ERROR =2;
    public static final Integer REEORCOUNT =3;
    //发件人
    public static final String FA_EMAIL ="1401499095@qq.com";
    //发件人密码
    public static final String MAIL_PASSWORD ="sehnflbbdkpqhihg";
    //连接的服务器
    public static final String MAIL_HOST ="smtp.qq.com";

    public  static final  String PASSWORD_EMAIL_ERROR="<h1 style=\"color:red\"><b>你好，你的账户连续三次密码错误。请确认被盗号风险！！</b></h1>";
    public  static final  String UPDATE_PASSWORD_EMAIL="123456";
    public static final int SMSCODE_IS_NULL = 3*60;
    public static final String SECRET_KEY=("PANZHINBLIULIULIU");
    public static final int VIP_GQI = 60*60;
    public static final int ZHIFU_NOT = 10; //未支付
    public static final int ZHIFU_SUCCESS = 20; //支付


    public static final int PAY_SUCCESS = 200; //支付成功


    public static final String ORDER_SUCCESS = "交易完成";

}
