package com.fh.shop.apiv4.product.biz;


import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.product.po.Product;

import java.util.List;

public interface IProductService {
    ServerResponse productList();

    List<Product> findList();

    void add(Product product);

    void del(Long id);

    List<Product> queryProductCount();
}
