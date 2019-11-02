package com.fh.shop.apiv4.vip.po;

import com.baomidou.mybatisplus.annotation.TableField;

public class Vip {
    private Long id;

    private String vip;

    private String password;

    private String realName;

    private String emali;

    private String phone;

    @TableField(exist = false)
    private String imgCode;

    //地区
    private Integer cate1;

    private Integer cate2;

    private Integer cate3;


    public String getEmali() {
        return emali;
    }

    public void setEmali(String emali) {
        this.emali = emali;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
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
}
