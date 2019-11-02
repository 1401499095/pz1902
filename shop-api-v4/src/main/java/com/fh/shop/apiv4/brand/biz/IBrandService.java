package com.fh.shop.apiv4.brand.biz;



import com.fh.shop.apiv4.brand.po.Brand;
import com.fh.shop.apiv4.brand.vo.ParmBrand;
import com.fh.shop.apiv4.conmmons.DataTableResult;
import com.fh.shop.apiv4.conmmons.ServerResponse;



public interface IBrandService {
    ServerResponse brandList();

    ServerResponse addBrand(Brand brand);

    ServerResponse delBrand(Integer id);

    ServerResponse toUpdateBrand(Integer id);

    ServerResponse updateBrand(Brand brand);

    ServerResponse deleteQuanBrand(Integer[] ids);

    DataTableResult findBrand(ParmBrand parmBrand);

    //ServerResponse findBrand(ParmBrand parmBrand);
}
