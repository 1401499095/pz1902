package com.fh.shop.apiv4.carArea.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.apiv4.carArea.po.CarArea;

import java.util.List;

public interface CarAreaMapper extends BaseMapper<CarArea> {

    List<CarArea> queryArea(Long vipId);
}
