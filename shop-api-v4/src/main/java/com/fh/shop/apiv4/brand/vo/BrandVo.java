package com.fh.shop.apiv4.brand.vo;

import java.io.Serializable;

public class BrandVo implements Serializable {

    private Integer id;

    private  String  BrandName;

    private Integer  hotBrand;
    //商标
    private String mainImagePath;

    //排序字段
    private Integer zdyid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZdyid() {
        return zdyid;
    }

    public void setZdyid(Integer zdyid) {
        this.zdyid = zdyid;
    }

    public String getMainImagePath() {
        return mainImagePath;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public Integer getHotBrand() {
        return hotBrand;
    }

    public void setHotBrand(Integer hotBrand) {
        this.hotBrand = hotBrand;
    }
}
