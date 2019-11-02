package com.fh.shop.apiv4.util;

public class KeyUtil {

   public static String buildVipKey(String vip , String uuid){
       return "Vip:"+vip+":"+uuid;
   }

    //购物车
    public static String buildCartKey(Long vipId){
        return "Vip:"+vipId;
    }

    //支付
    public static String buildPayKey(Long vipId){
        return "Pay:"+vipId;
    }


}
