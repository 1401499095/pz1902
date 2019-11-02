package com.fh.shop.apiv4.area.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.fh.shop.apiv4.area.mapper.IAreaMapper;
import com.fh.shop.apiv4.area.po.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("areaService")
public class IAreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;


    @Override
    public List<Area> queryAreaList(Integer id) {
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pId",id);
        return areaMapper.selectList(queryWrapper);
    }
}
