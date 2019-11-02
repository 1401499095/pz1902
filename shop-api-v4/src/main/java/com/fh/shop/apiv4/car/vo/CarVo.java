package com.fh.shop.apiv4.car.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CarVo implements Serializable {
         private Long totalCount;

         private String totalPrice;

         private List<CartItemVo> cartItemVoList=new ArrayList<>();


    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemVo> getCartItemVoList() {
        return cartItemVoList;
    }

    public void setCartItemVoList(List<CartItemVo> cartItemVoList) {
        this.cartItemVoList = cartItemVoList;
    }
}
