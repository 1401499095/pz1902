package com.fh.shop.apiv4.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.apiv4.product.po.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductMapper extends BaseMapper<Product> {

    Long updateConut(@Param("productId") Long productId, @Param("count") Long count);

    List<Product> queryProductCount();
}
