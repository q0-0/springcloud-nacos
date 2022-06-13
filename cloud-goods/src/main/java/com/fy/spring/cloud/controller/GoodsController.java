package com.fy.spring.cloud.controller;

import com.fy.spring.cloud.entity.Goods;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
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
 * @Description:  商品服务 提供方
 */

@RestController
@RequestMapping("goods")
public class GoodsController {
    @Resource
    RestTemplate restTemplate;
    @RequestMapping("findById/{id}")

    public Goods findById(@PathVariable("id") Integer id) {
        System.out.println("要查询的商品id是"+id);
        return  new Goods(1, "小米",99);

    }

    @PostMapping("save")
//    public Map saveGoods(@RequestBody Goods goods) {
//        System.out.println("新增商品id是"+goods);
//        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
//        stringObjectHashMap.put("code", "200");
//        stringObjectHashMap.put("msg", "新增成功");
//        return stringObjectHashMap;
//    }

    @GetMapping("save")
    public Map save(){
        String url = "http://cloud-goods/goods/findById/11";
        // 设置请求头
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("source", "cloud-orders");
        HttpEntity requestEntity = new HttpEntity(requestHeaders);
        // 发送除POST请求之外的其他请求
        ResponseEntity<Goods> goods = restTemplate.exchange(url, HttpMethod.GET,requestEntity, Goods.class);

        System.out.println("商品信息是:" + goods.getBody());
        //保存订单(本地调用)
        System.out.println("保存订单成功!!!");
        // 扣库存
        return  new HashMap(){{
            put("code", 200);
            put("msg", "success");
        }};
    }

}
