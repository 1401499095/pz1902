package com.fh.shop.apiv4.pay.biz;

import com.alibaba.fastjson.JSONObject;

import com.fh.shop.apiv4.conmmons.ResponceEnum;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.order.mapper.IOrderMapper;
import com.fh.shop.apiv4.order.po.Order;
import com.fh.shop.apiv4.pay.po.Pay;
import com.fh.shop.apiv4.paylog.mapper.PaylogMapper;
import com.fh.shop.apiv4.paylog.po.PayLog;
import com.fh.shop.apiv4.util.*;
import github.wxpay.sdk.WXPay;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("payService")
public class IPayServiceImpl implements IPayService{

    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private PaylogMapper paylogMapper;




    @Override
    public ServerResponse createNative(Long vipId) {
        //获取支付日志
        String payJson = RedisUtil.get(KeyUtil.buildPayKey(vipId));
        if (StringUtils.isEmpty(payJson)){
            return ServerResponse.error(ResponceEnum.PAY_ORDER_IS_NOY);
        }
        PayLog payLog = JSONObject.parseObject(payJson, PayLog.class);

        //生成id
        String orderIds = payLog.getOutTradeNo();
        int payMoney = BigDecimalUtil.mul(payLog.getPayMoney().toString(),"100").intValue();

        //下订单
        MyWxConfig config = new MyWxConfig();
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "飞狐-666");
            data.put("out_trade_no", orderIds);
            data.put("total_fee",payMoney+"");
            Date date = DateUtils.addMinutes(new Date(), 2);
            data.put("time_expire",DateUtil.data2str(date,DateUtil.yyyyMMddHHmmss));
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为微信扫码支付
            Map<String, String> resp = wxpay.unifiedOrder(data);
            //统一下单
            String returnCode = resp.get("return_code");
            String returnMsg = resp.get("return_msg");
            if (!returnCode.equalsIgnoreCase("SUCCESS")){
                System.out.println(returnMsg);
                 return ServerResponse.error(9999,"微信支付平台提示:"+returnMsg);
            }
            String resultCode = resp.get("result_code");
            String err_code_des = resp.get("err_code_des");
            if (!resultCode.equalsIgnoreCase("SUCCESS")){
                System.out.println(err_code_des);
                return ServerResponse.error(9999,"微信支付平台提示:"+err_code_des);
            }
            //二维码
            String code_url = resp.get("code_url");

            //创建对象返回前台
            Pay pay = new Pay();
            pay.setCodeUrl(code_url);
            pay.setOutTradeNo(payLog.getOutTradeNo());
            pay.setPayMoney(payLog.getPayMoney().toString());
            //将前台需要的数据返回给前台
            return ServerResponse.success(pay);
            //System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }

    //支付成功查询
    @Override
    public ServerResponse queryPay(Long vipId) {
        //获取日志表
        String logJson = RedisUtil.get(KeyUtil.buildPayKey(vipId));
        PayLog payLog = JSONObject.parseObject(logJson, PayLog.class);

        String outTradeNo = payLog.getOutTradeNo();

        MyWxConfig config = new MyWxConfig();
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no",outTradeNo);

            int count = 0;
            while (true){
                Map<String, String> resp = wxpay.orderQuery(data);
                System.out.println(resp);
                String returnCode = resp.get("return_code");
                String returnMsg = resp.get("return_msg");
                if (!returnCode.equalsIgnoreCase("SUCCESS")){
                    System.out.println(returnMsg);
                    return ServerResponse.success(9999,"微信支付平台提示:"+returnMsg);
                }
                String resultCode = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if (!resultCode.equalsIgnoreCase("SUCCESS")){
                    System.out.println(err_code_des);
                    return ServerResponse.success(9999,"微信支付平台提示:"+err_code_des);
                }

                String trade_state = resp.get("trade_state");
                if (trade_state.equalsIgnoreCase("SUCCESS")){
                    System.out.println("支付成功");
                    //================================================

                    String orderId = payLog.getOrderId();
                    //修改订单
                    Order order = new Order();
                    order.setId(orderId);
                    order.setStatus(SystemConst.ZHIFU_SUCCESS);
                    order.setPayTime(new Date());
                    order.setOrderStatusDescribe(SystemConst.ORDER_SUCCESS);
                    orderMapper.updateById(order);
                    //修改日志
                    PayLog payLog1 = new PayLog();
                    payLog1.setPayTime(new Date());
                    payLog1.setPayStatus(SystemConst.PAY_SUCCESS);
                    payLog1.setOutTradeNo(outTradeNo);
                    paylogMapper.updateById(payLog1);
                    //删除redis
                    RedisUtil.del(KeyUtil.buildPayKey(vipId));

                    return ServerResponse.success(ResponceEnum.ZF_SUCCESS);
                }
                count++;
                Thread.sleep(3000);
                if (count >= 100){
                    System.out.println("支付超时");
                    return ServerResponse.error(ResponceEnum.ZF_CAOS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.success();
    }
}
