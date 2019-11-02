package com.fh.shop.apiv4.area.biz;

import com.fh.shop.apiv4.area.po.Area;

import java.util.List;

public interface IAreaService {

    List<Area> queryAreaList(Integer id);

}
