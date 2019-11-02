package com.fh.shop.apiv4.brand.po;


import java.io.Serializable;

public class Brand  implements Serializable {
    private Integer id;

    private String brandName;
    //排序字段
    private Integer zdyid;
    //热销品牌
    private Integer hotBrand;
    //商标
    private String mainImagePath;


    public String getMainImagePath() {
        return mainImagePath;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getZdyid() {
        return zdyid;
    }

    public void setZdyid(Integer zdyid) {
        this.zdyid = zdyid;
    }

    public Integer getHotBrand() {
        return hotBrand;
    }

    public void setHotBrand(Integer hotBrand) {
        this.hotBrand = hotBrand;
    }
}
