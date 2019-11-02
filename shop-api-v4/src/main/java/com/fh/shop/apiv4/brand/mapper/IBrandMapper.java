package com.fh.shop.apiv4.brand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.fh.shop.apiv4.brand.po.Brand;
import com.fh.shop.apiv4.brand.vo.ParmBrand;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {

    List<Brand> findBrand(ParmBrand parmBrand);

    Long findBrandByCount(ParmBrand parmBrand);
}
