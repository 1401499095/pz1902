package com.fh.shop.apiv4.sort.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.sort.mapper.SortMapper;
import com.fh.shop.apiv4.sort.po.Sort;
import com.fh.shop.apiv4.sort.vo.SortVo;
import com.fh.shop.apiv4.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("sortService")
public class ISortServiceImpl implements ISortService {

    @Autowired
    private SortMapper sortMapper;


    @Override
    public ServerResponse sortList() {

        String redisSortList = RedisUtil.get("RedisSortList");
        if (StringUtils.isNotEmpty(redisSortList)){
            //不为空
            List<SortVo> sortVos = JSONObject.parseArray(redisSortList, SortVo.class);
            //List<Sort> sortList = new ArrayList<>();
            return ServerResponse.success(sortVos);
        }

        //通过sql去数据库查
        List<Sort> sortPos = sortMapper.selectList(null);
        List<SortVo> sortVoList = new ArrayList<>();
        //转vo
        for (Sort sortPos1 : sortPos) {
            SortVo sortVo = new SortVo();
            sortVo.setId(sortPos1.getId());
            sortVo.setName(sortPos1.getName());
            sortVo.setFid(sortPos1.getpId());
            sortVoList.add(sortVo);
        }
        //转成json字符串
        String RedisJson = JSONObject.toJSONString(sortVoList);

        //存放到缓存服务器中
        RedisUtil.set("RedisSortList",RedisJson);
        return ServerResponse.success(sortVoList);
    }
}
