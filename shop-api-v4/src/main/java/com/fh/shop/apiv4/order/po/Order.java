package com.fh.shop.apiv4.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Order implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;//主键 订单id

    private Long userId;//用户id

    private Integer status;//订单状态(交易状态)

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//订单创建时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;//订单支付时间

    private String totalPrice;//订单总价

    private Integer payType;//支付类型

    private Long totalCount;//订单总个数

    private String orderStatusDescribe;//订单状态描述

    private String consignee;//收货人

    private String consigneePhone;//收货人电话

    private String address;//收货人地址

    private String postcode;//邮编

    private BigDecimal postage;//邮费

    private Integer invoice;//发票

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderCloseTime;//订单关闭时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderSuccessTime;//订单完成时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shipmentsTime;//订单发货时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderCommentTime;//订单完成评论时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getOrderStatusDescribe() {
        return orderStatusDescribe;
    }

    public void setOrderStatusDescribe(String orderStatusDescribe) {
        this.orderStatusDescribe = orderStatusDescribe;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public Date getOrderCloseTime() {
        return orderCloseTime;
    }

    public void setOrderCloseTime(Date orderCloseTime) {
        this.orderCloseTime = orderCloseTime;
    }

    public Date getOrderSuccessTime() {
        return orderSuccessTime;
    }

    public void setOrderSuccessTime(Date orderSuccessTime) {
        this.orderSuccessTime = orderSuccessTime;
    }

    public Date getShipmentsTime() {
        return shipmentsTime;
    }

    public void setShipmentsTime(Date shipmentsTime) {
        this.shipmentsTime = shipmentsTime;
    }

    public Date getOrderCommentTime() {
        return orderCommentTime;
    }

    public void setOrderCommentTime(Date orderCommentTime) {
        this.orderCommentTime = orderCommentTime;
    }
}
