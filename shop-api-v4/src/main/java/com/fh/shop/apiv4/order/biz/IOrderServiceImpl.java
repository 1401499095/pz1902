package com.fh.shop.apiv4.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import com.fh.shop.apiv4.car.vo.CarVo;
import com.fh.shop.apiv4.car.vo.CartItemVo;
import com.fh.shop.apiv4.conmmons.ResponceEnum;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.order.mapper.IOrderDetailMapper;
import com.fh.shop.apiv4.order.mapper.IOrderMapper;
import com.fh.shop.apiv4.order.param.OrderParam;
import com.fh.shop.apiv4.order.po.Order;
import com.fh.shop.apiv4.order.po.OrderDetail;
import com.fh.shop.apiv4.paylog.mapper.PaylogMapper;
import com.fh.shop.apiv4.paylog.po.PayLog;
import com.fh.shop.apiv4.product.mapper.IProductMapper;
import com.fh.shop.apiv4.product.po.Product;
import com.fh.shop.apiv4.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service("orderService")
public class IOrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderDetailMapper orderDetailMapper;
    @Autowired
    private IProductMapper productMapper;
    @Autowired
    private PaylogMapper paylogMapper;

    //新增订单
    @Override
    public ServerResponse addOrder(Long vipId, OrderParam orderParam) {
        //判断该用户购物车是否为空
        String cartItmeJson = RedisUtil.hget("cartName", KeyUtil.buildCartKey(vipId));
        if (cartItmeJson == null) {
            //购物车 为空
            return ServerResponse.error(ResponceEnum.CART_IS_NOT);
        }
        //将json数据转为java对象
        CarVo cartVo = JSONObject.parseObject(cartItmeJson,CarVo.class);

        //生成订单id
        String orderId = IdWorker.getTimeId();

        //新增订单详情
        List<CartItemVo> cartItemVoList = cartVo.getCartItemVoList();
        List<CartItemVo> arrCarItemVo = new ArrayList<>();

        for (Iterator<CartItemVo> iterator = cartItemVoList.iterator();iterator.hasNext();){
            CartItemVo cartItemVo = iterator.next();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(cartItemVo.getProductId());
            orderDetail.setProductName(cartItemVo.getProductName());
            orderDetail.setCount(cartItemVo.getCount());
            orderDetail.setPrice(new BigDecimal(cartItemVo.getPrice()));
            orderDetail.setImage(cartItemVo.getImage());
            orderDetail.setSubTotalCount(cartItemVo.getCount().toString());

            //减库存
            Long productId = orderDetail.getProductId();
            Long count = orderDetail.getCount();
            Product product = productMapper.selectById(productId);

            Integer stock = product.getStock();

            if (stock >= count){
                //插入订单详情
                orderDetailMapper.insert(orderDetail);
                //修改库存
                Long counts  = productMapper.updateConut(productId,count);
                //对数据库影响0行
                if (counts == 0){
                    //库存不足的新增到集合中
                    arrCarItemVo.add(cartItemVo);
                    //从购物车中删除
                    iterator.remove();
                }
            }else {
                //库存不足的新增到集合中
                arrCarItemVo.add(cartItemVo);
                //从购物车中删除
                iterator.remove();
            }

        }
        //更新购物车
        Long totalCount =0L;
        BigDecimal totalPrice=new BigDecimal(0);
        for (CartItemVo cartItemVo : cartItemVoList) {
            totalCount += cartItemVo.getCount();
            totalPrice = BigDecimalUtil.add(totalPrice.toString(), cartItemVo.getSubTotalPrice());
        }
        cartVo.setTotalCount(totalCount);
        cartVo.setTotalPrice(totalPrice.toString());

        //新增订单
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(vipId);
        order.setStatus(SystemConst.ZHIFU_NOT);//未支付状态
        order.setPayType(orderParam.getPayType());//支付方式
        order.setCreateTime(new Date()); //订单创建时间
        order.setTotalCount(cartVo.getTotalCount());//订单总个数
        order.setTotalPrice(cartVo.getTotalPrice());//总价格
        /*order.setOrderStatusDescribe("");//订单描述
        order.setConsignee("");//收货人
        order.setConsigneePhone("");//收货人电话
        order.setAddress("");//收货地址
        order.setPostcode("");//邮编
        order.setPostage(new BigDecimal(1));//邮费*/
        order.setInvoice(orderParam.getInvoice());//发票
        //保存到数据库
        orderMapper.insert(order);

        //插入支付日志
        PayLog payLog = new PayLog();
        //雪花生成id
        payLog.setOutTradeNo(IdUtil.createId());
        payLog.setOrderId(orderId); //订单号
        payLog.setCreateTime(new Date());  //创建时间
        payLog.setPayMoney(new BigDecimal(order.getTotalPrice()));  //支付金额
        payLog.setPayType(orderParam.getPayType()); //支付方式
        payLog.setUserId(vipId); //用户id
        //payLog.setPayStatus();  //支付状态  默认是 0未支付   1是支付
        //payLog.setPayTime();   //支付时间
        //payLog.setTransactionId(); //支付流水号
        paylogMapper.insert(payLog);

        //将支付日志插入redis
        String jsonString = JSONObject.toJSONString(payLog);
        RedisUtil.set(KeyUtil.buildPayKey(vipId),jsonString);

        //清空购物车
        RedisUtil.hdel("cartName", KeyUtil.buildCartKey(vipId));
        //返回的是库存不足的商品
        return ServerResponse.success(arrCarItemVo);
    }

    //查询
    @Override
    public List<String> selectOutTimeOrder() {
        List<String> list = orderMapper.selectOutTimeOrder();
        return list;
    }

    //删除
    @Override
    public void deleteOrderBatch(List<String> list) {
        for (String s : list) {
            orderMapper.deleteById(s);
            paylogMapper.deleteById(s);
            orderDetailMapper.deleteById(s);
        }

    }


}
