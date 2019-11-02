package com.fh.shop.apiv4.car.biz;

import com.alibaba.fastjson.JSONObject;


import com.fh.shop.apiv4.car.vo.CarVo;
import com.fh.shop.apiv4.car.vo.CartItemVo;
import com.fh.shop.apiv4.conmmons.ResponceEnum;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.product.mapper.IProductMapper;
import com.fh.shop.apiv4.product.po.Product;
import com.fh.shop.apiv4.util.BigDecimalUtil;
import com.fh.shop.apiv4.util.KeyUtil;
import com.fh.shop.apiv4.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("cartservice")
public class CartServiceImpl implements ICarService{
      @Autowired
       private IProductMapper productMapper;



    //删除
    @Override
    public ServerResponse deleteCart(Long userVipVoId, Long productId) {
        //  购物车是否存在
        String cartJson = RedisUtil.hget("cartName", KeyUtil.buildCartKey(userVipVoId));
        if(StringUtils.isEmpty(cartJson)){
            //购物车为空
            return ServerResponse.error(ResponceEnum.CART_IS_NOT);
        }
        //  购物车有商品
        CarVo cartVo = JSONObject.parseObject(cartJson, CarVo.class);
        boolean deleteSuccess = deleteProductFromCart(cartVo, productId);
        if(!deleteSuccess){
            //购物车没有商品
            return ServerResponse.error(ResponceEnum.CART_PRODUCT_NOT);
        }
        // 判断购物车是否有商品
        if(cartVo.getCartItemVoList().size() == 0){
            // 删除整个购物车
            RedisUtil.hdel("cartName",KeyUtil.buildCartKey(userVipVoId));
        }else{
            //  更新购物车
            updateCart(cartVo);
            //  存入reds
            String listJson = JSONObject.toJSONString(cartVo);
            RedisUtil.hset("cartName",KeyUtil.buildCartKey(userVipVoId),listJson);
        }
        return ServerResponse.success();
    }

    private boolean deleteProductFromCart(CarVo cartVo,Long productId){
        boolean deleteScuuess = false;
        List<CartItemVo> cartItemVoList = cartVo.getCartItemVoList();
        for (CartItemVo item : cartItemVoList) {
            if(item.getProductId() == productId){
                cartItemVoList.remove(item);
                deleteScuuess = true;
                break;
            }
        }
        return deleteScuuess;
    }

    private void updateCart(CarVo cartVo) {
        Long totalCount = 0L;
        BigDecimal totalPrice = new BigDecimal(0);
        List<CartItemVo> cartItemVoList = cartVo.getCartItemVoList();
        for (CartItemVo item : cartItemVoList) {
            totalCount  += item.getCount();
            String itemPrice = BigDecimalUtil.mul(item.getPrice(), item.getCount().toString()).toString();
            totalPrice = BigDecimalUtil.add(totalPrice.toString(),itemPrice);
        }
        cartVo.setTotalCount(totalCount);
        cartVo.setTotalPrice(totalPrice.toString());

    }



    /**
     * 查询购物车
     * @param vipId
     * @return
     */
    @Override
    public ServerResponse findCart(Long vipId) {
        String hgetJson = RedisUtil.hget("cartName", KeyUtil.buildCartKey(vipId));
        if(StringUtils.isEmpty(hgetJson)){
            return ServerResponse.error(ResponceEnum.CART_IS_NOT);
        }
        CarVo carVo = JSONObject.parseObject(hgetJson, CarVo.class);
        return ServerResponse.success(carVo);
    }

    /**
     * 添加购物车
     * @param productId
     * @param count
     * @param vipId
     * @return
     */
    @Override
    public ServerResponse addCar(Long productId, Long count, Long vipId) {
        Product product = productMapper.selectById(productId);
        //判断商品是否存在
        if(product == null){
           return ServerResponse.error(ResponceEnum.PRODUCT_IS_NOT);//商品不存在
        }
        //判断商品是否上下架
        Integer shelves = product.getShelves();
        if(shelves == 2){
            return ServerResponse.error(ResponceEnum.PRODUCT_NOT_SJ);
        }
        //判断该用户是否有购物车
        String cartJson = RedisUtil.hget("cartName", KeyUtil.buildCartKey(vipId));
        //如果没有则构建购物车
         if(StringUtils.isEmpty(cartJson)){
             return buildCartAndAddItem(productId, count, vipId, product);
         }
        //如果有购物车则在redis中取出对应的数据
        CarVo carVo = JSONObject.parseObject(cartJson, CarVo.class);
        CartItemVo cartItrmVo = getProduct(productId, carVo);
        //如果商品不存在则加入redis
        if(cartItrmVo == null){
            return addItem(productId, count, vipId, product, carVo);
        }
        //如果有则更新商品数量统计价格(小计)
        return updateItem(count, vipId, carVo, cartItrmVo);
    }


    private ServerResponse updateItem(Long count, Long vipId, CarVo carVo, CartItemVo cartItrmVo) {
        cartItrmVo.setCount(cartItrmVo.getCount()+count);
        //获取处理后的个数
        Long counttt = cartItrmVo.getCount();
        //如果个数为0  删除该商品
        if (counttt == 0){
                deleteProductFromCart(carVo,cartItrmVo);
        }

        String subTotalPrice = BigDecimalUtil.mul(cartItrmVo.getPrice(), cartItrmVo.getCount().toString()).toString();
        cartItrmVo.setSubTotalPrice(subTotalPrice);
        //更新购物车
        updateCart(carVo,vipId);
        return ServerResponse.success();
    }
    //商品不存在添加商品
    private ServerResponse addItem(Long productId, Long count, Long vipId, Product product, CarVo carVo) {
        CartItemVo itemVo=buildCartItem(productId,count,product);
        carVo.getCartItemVoList().add(itemVo);
        //更新购物车【商品的总结以及个数】
        updateCart(carVo,vipId);
        return ServerResponse.success();
    }

    private ServerResponse buildCartAndAddItem(Long productId, Long count, Long vipId, Product product) {
        //创建购物车
        CarVo carVo=new CarVo();
        //创建商品
        return addItem(productId, count, vipId, product, carVo);
    }

    private CartItemVo getProduct(Long productId,CarVo carVo) {
        CartItemVo product=null;
        List<CartItemVo> cartItemVoList = carVo.getCartItemVoList();
        for (CartItemVo itemVo : cartItemVoList) {
            if(itemVo.getProductId() == productId){
                product=itemVo;
                break;
            }
        }
        return product;
    }

    private void updateCart(CarVo carVo,Long vipId) {
        List<CartItemVo> cartItemVoList=carVo.getCartItemVoList();
        Long totalCount =0L;
        BigDecimal totalPrice=new BigDecimal(0);
        for (CartItemVo cartItemVo : cartItemVoList) {
            totalCount += cartItemVo.getCount();
            totalPrice = BigDecimalUtil.add(totalPrice.toString(), cartItemVo.getSubTotalPrice());
        }
        carVo.setTotalCount(totalCount);
        carVo.setTotalPrice(totalPrice.toString());
        //更新redis
        String cartVoJson = JSONObject.toJSONString(carVo);
        RedisUtil.hset("cartName",KeyUtil.buildCartKey(vipId),cartVoJson);
    }

    //添加创建商品
    private CartItemVo buildCartItem(Long productId, Long count, Product product) {
        CartItemVo itemVo = new CartItemVo();
        //构建商品
        itemVo.setImage(product.getMainImagePath());
        itemVo.setProductName(product.getProductName());
        itemVo.setCount(count);
        itemVo.setProductId(productId);
        String price = product.getPrice().toString();
        itemVo.setPrice(price);
        String subTotalPrice = BigDecimalUtil.mul(price, String.valueOf(count)).toString();
        itemVo.setSubTotalPrice(subTotalPrice);
        return itemVo;
    }

    private void deleteProductFromCart(CarVo carVo, CartItemVo cartItrmVo) {
        List<CartItemVo> cartItemVoList = carVo.getCartItemVoList();
        for (CartItemVo itemVo : cartItemVoList) {
            if(itemVo.getProductId() == cartItrmVo.getProductId()){
                cartItemVoList.remove(itemVo);
                break;
            }
        }
    }
}
