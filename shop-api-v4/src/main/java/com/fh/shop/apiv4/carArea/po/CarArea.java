package com.fh.shop.apiv4.carArea.po;

import com.baomidou.mybatisplus.annotation.TableField;

public class CarArea {

    private Long id;

    private Long userId; //会员id；

    private String consignee; //收货人

    private String consigneePhone; //收货人电话
    //  三级地区
    private Integer cate1;

    private Integer cate2;

    private Integer cate3;

    private String xxArea;  //详细街道

    //分类名
    @TableField(exist = false)
    private String areaName;



    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getCate1() {
        return cate1;
    }

    public void setCate1(Integer cate1) {
        this.cate1 = cate1;
    }

    public Integer getCate2() {
        return cate2;
    }

    public void setCate2(Integer cate2) {
        this.cate2 = cate2;
    }

    public Integer getCate3() {
        return cate3;
    }

    public void setCate3(Integer cate3) {
        this.cate3 = cate3;
    }

    public String getXxArea() {
        return xxArea;
    }

    public void setXxArea(String xxArea) {
        this.xxArea = xxArea;
    }
}
