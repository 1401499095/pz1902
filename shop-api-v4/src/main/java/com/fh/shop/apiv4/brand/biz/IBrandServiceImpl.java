package com.fh.shop.apiv4.brand.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.apiv4.brand.mapper.IBrandMapper;
import com.fh.shop.apiv4.brand.po.Brand;
import com.fh.shop.apiv4.brand.vo.BrandVo;
import com.fh.shop.apiv4.brand.vo.ParmBrand;
import com.fh.shop.apiv4.conmmons.DataTableResult;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("brandService")
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;


    @Override
    public ServerResponse brandList() {
        //判断缓存是否存在
        String redisBrandList = RedisUtil.get("RedisBrandList");
        if (StringUtils.isNotEmpty(redisBrandList)){
            //为空
            List<BrandVo> BrandVo = JSONObject.parseArray(redisBrandList, BrandVo.class);
            return ServerResponse.success(BrandVo);
        }
        //不为空
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        //热销
        queryWrapper.eq("hotBrand",1);
        List<Brand> brands = brandMapper.selectList(queryWrapper);
        //java转json
        String brandJson = JSONObject.toJSONString(brands);
        //存到缓存中
        RedisUtil.set("RedisBrandList",brandJson);
        //转vo
        List<BrandVo> brandVos=new ArrayList<>();
        if(brands!=null && brands.size()>0 ){
            for (Brand brand : brands) {
                BrandVo brandVos1 = new BrandVo();
                brandVos1.setBrandName(brand.getBrandName());
                brandVos1.setHotBrand(brand.getHotBrand());
                brandVos1.setMainImagePath(brand.getMainImagePath());
                brandVos.add(brandVos1);
            }
        }
        return ServerResponse.success(brandVos);
    }



    @Override
    public ServerResponse addBrand(Brand brand) {
        brandMapper.insert(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse delBrand(Integer id) {
        brandMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse toUpdateBrand(Integer id) {
        Brand brand = brandMapper.selectById(id);
        return ServerResponse.success(brand);
    }

    @Override
    public ServerResponse updateBrand(Brand brand) {
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteQuanBrand(Integer[] ids) {
        List<Integer> idList = new ArrayList<>();
        for (Integer id : ids) {
            idList.add(id);
        }
        brandMapper.deleteBatchIds(idList);
        return ServerResponse.success();
    }

    @Override
    public DataTableResult findBrand(ParmBrand parmBrand) {
        //查询总条数
        Long count = brandMapper.findBrandByCount(parmBrand);
        //查询对应的数据
        List<Brand> list = brandMapper.findBrand(parmBrand);
        //返回DataTableResult
        DataTableResult dataTableResult = new DataTableResult(parmBrand.getDraw(),count,count,list);
        return dataTableResult;
    }

}
