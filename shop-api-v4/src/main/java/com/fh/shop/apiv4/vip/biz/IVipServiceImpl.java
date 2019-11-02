package com.fh.shop.apiv4.vip.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.apiv4.conmmons.ResponceEnum;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.util.KeyUtil;
import com.fh.shop.apiv4.util.Md5Util;
import com.fh.shop.apiv4.util.RedisUtil;
import com.fh.shop.apiv4.util.SystemConst;
import com.fh.shop.apiv4.vip.mapper.VipMapper;
import com.fh.shop.apiv4.vip.po.Vip;
import com.fh.shop.apiv4.vip.vo.VipVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service("vipService")
public class IVipServiceImpl implements IVipService {

    @Autowired
    private VipMapper vipMapper;

    //注册用户
    @Override
    public ServerResponse add(Vip vip) {
        //==================非空验证=====================
        //手机
        if (StringUtils.isEmpty(vip.getPhone())){
            return ServerResponse.error(ResponceEnum.PHPONE_IS_NULL);
        }
        //邮箱
        if (StringUtils.isEmpty(vip.getEmali())){
            return ServerResponse.error(ResponceEnum.EMALI_IS_NULL);
        }
        //用户名
        if (StringUtils.isEmpty(vip.getVip())){
            return ServerResponse.error(ResponceEnum.VIP_NAME_IS_NULL);
        }

        //==================表单信息对比数据库验证==========================
        //验证该手机是否被注册
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",vip.getPhone());
        Vip vip1 = vipMapper.selectOne(queryWrapper);
        if (vip1 != null){
            return ServerResponse.error(ResponceEnum.PHPONE_IS_CZ);
        }
        //用户名是否被注册
        QueryWrapper<Vip> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("vip",vip.getVip());
        Vip vip2 = vipMapper.selectOne(queryWrapper1);
        if (vip2 != null){
            return ServerResponse.error(ResponceEnum.VIP_NAME_IS_CZ);
        }
        //邮箱
        QueryWrapper<Vip> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("emali",vip.getEmali());
        Vip vip3 = vipMapper.selectOne(queryWrapper2);
        if (vip3 != null){
            return ServerResponse.error(ResponceEnum.EMALI_IS_CZ);
        }

        //获得验证码  json字符串和string字符串可以直接做比较
        String CodeJson = RedisUtil.get("code" + vip.getPhone());
        //判断验证码是否一致
        if (CodeJson.equals(vip.getImgCode())) {
            //验证码一致  新增会员用户
            vipMapper.insert(vip);
        }
        return ServerResponse.success();
    }

    //用户名查询
    @Override
    public ServerResponse queryName(String vip) {
        QueryWrapper<Vip> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("vip",vip);
        Vip vip4 = vipMapper.selectOne(queryWrapper3);
        return ServerResponse.success(vip4);
    }
    //emali查询
    @Override
    public ServerResponse queryEmali(String emali) {
        QueryWrapper<Vip> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("emali",emali);
        Vip vip5 = vipMapper.selectOne(queryWrapper4);
        return ServerResponse.success(vip5);
    }

    //手机查询
    @Override
    public ServerResponse queryPhone(String phone) {
        QueryWrapper<Vip> queryWrapper5 = new QueryWrapper<>();
        queryWrapper5.eq("phone",phone);
        Vip vip6 = vipMapper.selectOne(queryWrapper5);
        return ServerResponse.success(vip6);
    }

    //登陆
    @Override
    public ServerResponse login(Vip vip) {
        String vip1 = vip.getVip();
        String password = vip.getPassword();

        if (StringUtils.isEmpty(vip1)){
            //用户名为空
            return ServerResponse.error(ResponceEnum.VIP_NAME_IS_NULL);
        }
        if (StringUtils.isEmpty(password)){
            //密码为空
            return ServerResponse.error(ResponceEnum.PASSWORD_IS_NULL);
        }
        //根据用户名条件查询
        QueryWrapper<Vip> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("vip",vip1);
        Vip vip4 = vipMapper.selectOne(queryWrapper3);

        Long id = vip4.getId();
        //判断用户名是否存在
        if (vip4 == null){
            //用户名不存在
            return ServerResponse.error(ResponceEnum.VIPNAME_IS_NOTCZ);
        }
        if (!vip4.getPassword().equals(password)){
            //密码不一致
            return ServerResponse.error(ResponceEnum.PWD_IS_ERROR);
        }
        //构建要返回给客户端用户信息
        VipVo vipVo = new VipVo();
        vipVo.setVip(vip1);
        String uuid = UUID.randomUUID().toString();
        vipVo.setUuid(uuid);
        vipVo.setId(id);

        //对象转json
        String vipJson = JSONObject.toJSONString(vipVo);
        //64编码
        String vipEncoder64 = Base64.getEncoder().encodeToString(vipJson.getBytes());
        //签名  防止数据在前台被篡改
        String sign = Md5Util.sign(vipEncoder64, SystemConst.SECRET_KEY);
        //再次进行64编码
        String vip64 = Base64.getEncoder().encodeToString(sign.getBytes());
        //存放到redis中   后续判断是否过时
        RedisUtil.setex(KeyUtil.buildVipKey(vip1,uuid),SystemConst.VIP_GQI,"1");
        //响应客户端   对象字符串   +    对象＋密钥字符串
        return ServerResponse.success(vipEncoder64+"."+vip64);
    }

    @Override
    public ServerResponse vipInfo(String vip) {
        QueryWrapper<Vip> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("vip",vip);
        Vip vip4 = vipMapper.selectOne(queryWrapper3);
        String realName = vip4.getRealName();
        return ServerResponse.success(realName);
    }


    //免密登陆
    @Override
    public ServerResponse mlogin(String phone, String code) {

        String CodeJson = RedisUtil.get("code" + phone);
        //验证码不一致
        if (!CodeJson.equals(code)) {
            return ServerResponse.error(ResponceEnum.CODE_IS_NO);
        }

        //根据手机号查对象
        QueryWrapper<Vip> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        Vip vip = vipMapper.selectOne(queryWrapper);

        //获取到用户名  用于保存到redis中
        String vip1 = vip.getVip();

        //构建要返回给客户端用户信息
        VipVo vipVo = new VipVo();
        vipVo.setId(vip.getId());
        vipVo.setVip(vip.getVip());
        String uuid = UUID.randomUUID().toString();
        vipVo.setUuid(uuid);

        //对象转json
        String vipJson = JSONObject.toJSONString(vipVo);
        //64编码
        String vipEncoder64 = Base64.getEncoder().encodeToString(vipJson.getBytes());
        //签名  防止数据在前台被篡改
        String sign = Md5Util.sign(vipEncoder64, SystemConst.SECRET_KEY);
        //再次进行64编码
        String vip64 = Base64.getEncoder().encodeToString(sign.getBytes());
        //存放到redis中   后续判断是否过时
        RedisUtil.setex(KeyUtil.buildVipKey(vip1,uuid),SystemConst.VIP_GQI,"1");
        //响应客户端   对象字符串   +    对象＋密钥字符串
            return ServerResponse.success(vipEncoder64+"."+vip64);
        }

}