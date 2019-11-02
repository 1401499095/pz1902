package com.fh.shop.apiv4.order.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;


@TableName("t_order_detail")
public class OrderDetail implements Serializable {
    private String orderId;//订单id

    private Long userId;//用户ID

    private Long productId;//商品id

    private String productName;//商品名字

    private String image;//图片

    private BigDecimal price;//价格

    private Long count;//个数

    private String subTotalCount;//小计


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getSubTotalCount() {
        return subTotalCount;
    }

    public void setSubTotalCount(String subTotalCount) {
        this.subTotalCount = subTotalCount;
    }
}
