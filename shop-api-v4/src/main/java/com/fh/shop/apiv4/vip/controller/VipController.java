package com.fh.shop.apiv4.vip.controller;


import com.fh.shop.apiv4.annotation.Check;
import com.fh.shop.apiv4.conmmons.ServerResponse;
import com.fh.shop.apiv4.product.biz.IProductService;
import com.fh.shop.apiv4.util.KeyUtil;
import com.fh.shop.apiv4.util.RedisUtil;
import com.fh.shop.apiv4.vip.biz.IVipService;
import com.fh.shop.apiv4.vip.po.Vip;
import com.fh.shop.apiv4.vip.vo.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vips")
@CrossOrigin("*")
public class VipController {

    @Resource(name = "vipService")
    private IVipService iVipService;

    @Autowired
    private IProductService productService;

    @Autowired
    private HttpServletRequest request;

    //登陆
    @GetMapping(value = "login")
    public ServerResponse login(Vip vip){
        return iVipService.login(vip);
    }

    /*//发库存不足发短信
    @Scheduled(cron = "0/3 * * * * ?")
    public void checkProductCount() throws Exception {
        List<Product> list=productService.queryProductCount();
        String str="<br>";
        for (Product product : list) {
            str+="商品名:"+product.getProductName()+"---价格："+product.getPrice()+"---库存："+product.getStock()+"<br>";
        }
        str+="<br>发送人：潘志";
        MailUtil.sendMail("库存不足！",str,"1091917557@qq.com");
        System.out.println(0);
    }*/


    //免密登陆
    @GetMapping(value = "mlogin")
    public ServerResponse mlogin(String phone , String code){
        return iVipService.mlogin(phone,code);
    }


    //注册
    @PostMapping
    public ServerResponse add(Vip vip){
        return iVipService.add(vip);
    }

    //导航条用户信息
    @GetMapping(value = "vipInfo")
    @Check
    public ServerResponse vipInfo(){
        VipVo vipVo = (VipVo)request.getAttribute("vipVo");
        //获取用户名
        String vip = vipVo.getVip();
        //利用查出来的会员名 去查真实姓名
        return iVipService.vipInfo(vip);
    }

    //退出
    @GetMapping(value = "logout")
    @Check
    public ServerResponse logout(){
        VipVo vipVo = (VipVo)request.getAttribute("vipVo");
        //获取用户名
        String vip = vipVo.getVip();
        String uuid = vipVo.getUuid();
        RedisUtil.del(KeyUtil.buildVipKey(vip,uuid));
        return ServerResponse.success();
    }

    //========================信息的唯一性=============================
    //用户名
    @GetMapping(value = "queryName")
    public ServerResponse queryName(String vip){
        return iVipService.queryName(vip);
    }
    //邮箱
    @GetMapping(value = "queryEmali")
    public ServerResponse queryEmali(String emali){
        return iVipService.queryEmali(emali);
    }
    //手机号
    @GetMapping(value = "queryPhone")
    public ServerResponse queryPhone(String phone){
        return iVipService.queryPhone(phone);
    }
}
