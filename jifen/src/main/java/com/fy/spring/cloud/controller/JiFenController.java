package com.fy.spring.cloud.controller;

import com.fy.spring.cloud.entity.JiFen;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　 ┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　  ┃
 * 　　┃　　　　　　 ┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　 ┃
 * 　　┗━┓　　　┏━┛Faith and purpose must always be in the heart of the programmer
 * 　　　　┃　　　┃
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * @Author: Flying
 * @Description:
 */

@RestController
@RequestMapping("jifen")
@RefreshScope //动态刷新配置
public class JiFenController {

    @Value("${pic.url}")
    private String picUrl;

    @GetMapping("url")
    public String url(){
        return picUrl;
    }









    @PostMapping("save")
    public Map save(@RequestBody JiFen jiFen) {
        System.out.println("调用了积分保存接口");
        System.out.println(jiFen);
        return new HashMap(){{
            put("isSuccess",true);
            put("msg","save success");
        }};
    }

    @PostMapping(value = "update")
    public Map update(@RequestBody JiFen jifen) {

        System.out.println(jifen);
        return new HashMap(){{
            put("isSuccess",true);
            put("msg","update success");
        }};
    }

    @GetMapping(value = "delete")
    public Map deleteById(Integer jifenId) {
        System.out.println("删除id为"+jifenId+"的积分信息");
        return new HashMap(){{
            put("isSuccess",true);
            put("msg","delete success");
        }};
    }
    @GetMapping(value = "{jifenId}")
    public JiFen findJifenById(@PathVariable Integer jifenId) {
        System.out.println("已经查询到"+jifenId+"积分数据");
        return new JiFen(jifenId, 12,jifenId+"号积分");
    }


    @GetMapping(value = "/search")
    public JiFen search(Integer uid,String type) {
        System.out.println("uid:"+uid+"type:"+type);
        return new JiFen(uid, 12,type);
    }

    @PostMapping(value = "/searchByEntity")
    public List<JiFen> searchMap(@RequestBody  JiFen jifen) {

        System.out.println(jifen);

        List<JiFen> jifens = new ArrayList<JiFen>();
        jifens.add(new JiFen(110,12,"下单积分"));
        jifens.add(new JiFen(111,18,"支付积分"));
        return  jifens;
    }


}
