package com.fh.shop.apiv4.order.param;


import java.io.Serializable;
public class OrderParam implements Serializable {

    private Integer payType;//支付方式

    private Integer invoice;//发票




    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}
