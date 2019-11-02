package com.fh.shop.apiv4.product.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.product.mapper.IProductMapper;
import com.fh.shop.apiv4.product.po.Product;
import com.fh.shop.apiv4.product.vo.ProductVo;
import com.fh.shop.apiv4.util.DateUtil;
import com.fh.shop.apiv4.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class IProductServiceImpl implements IProductService {

    @Autowired
    private IProductMapper productMapper;

    @Override
    public ServerResponse productList() {
        //判断缓存中是否有
        String redisProductList = RedisUtil.get("RedisProductList");
        if (StringUtils.isNotEmpty(redisProductList)){
            //不为空 从缓存中取数据
            //将json格式转换成java对象形式
            List<ProductVo> productVos = JSONObject.parseArray(redisProductList, ProductVo.class);
            return ServerResponse.success(productVos);
        }

        //缓存为空 从数据库取数据
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        //id倒叙
        queryWrapper.orderByDesc("id");
        //上架
        queryWrapper.eq("shelves",1);

        List<Product> products = productMapper.selectList(queryWrapper);
        //将java对象格式转换成json格式
        String productListJson = JSONObject.toJSONString(products);
        //将json格式字符串存到缓存中去
        RedisUtil.setex("RedisProductList",60,productListJson);
        //转vo
        List<ProductVo> productVos1 = buildProductVo(products);
        return ServerResponse.success(productVos1);
    }

    //测试查询接口
    @Override
    public List<Product> findList() {
        List<Product> products = productMapper.selectList(null);
        return products;
    }
    //新增
    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }
    //删除
    @Override
    public void del(Long id) {
        productMapper.deleteById(id);
    }

    //定时器
    @Override
    public List<Product> queryProductCount() {
        return productMapper.queryProductCount();
    }


    private List<ProductVo> buildProductVo(List<Product> products) {
        List<ProductVo> productVos=new ArrayList<>();
        if(products!=null && products.size()>0 ){
            for (Product produc : products) {
                ProductVo productVo1 = new ProductVo();
                productVo1.setId(produc.getId());
                productVo1.setPrice(produc.getPrice().toString());
                productVo1.setProducedDate(DateUtil.data2str(produc.getProducedDate(),DateUtil.Y_M_D));
                productVo1.setProductName(produc.getProductName());
                productVo1.setMainImagePath(produc.getMainImagePath());
                productVos.add(productVo1);
            }
        }
        return productVos;
    }
}
