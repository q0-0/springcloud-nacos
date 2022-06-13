package com.qf.spring.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.fy.spring.cloud.entity.Goods;
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
 * @Description:
 */

@RestController
@RequestMapping("order")
public class OrdersController {
    @Resource
    RestTemplate template;
    //保存订单的接口   订单调用商品服务
    @GetMapping("save")
    public Map save(){
        String url = "http://cloud-goods/goods/findById/11";
        Goods goods = template.getForObject(url, Goods.class);
        System.err.println(goods);

        return new HashMap(){
            {
                put("code",200);
                put("msg","success");
            }
        };
    }


    //保存订单的接口
    @GetMapping("save2")
    public Map save2(){
        String url = "http://cloud-goods/goods/save";
        Goods oppo = new Goods(2,"oppo", 180);
        Map map = template.postForObject(url, oppo, Map.class);
        return map;
    }

    //保存订单的接口
    @GetMapping("save3")
    public Map save3(){
        String url = "http://cloud-goods/goods/findById/11";
        ResponseEntity<Goods> goods = template.getForEntity(url, Goods.class);
        System.err.println("商品信息是：" + goods);
        return new HashMap(){{
            put("code", 200);
            put("msg", "success");
        }};
    }

    @GetMapping("save4")
    public Map save4(){
        String url = "http://cloud-goods/goods/save";
        Goods goods = new Goods(1, "hw", 190);
        ResponseEntity<Map> entity = template.postForEntity(url, goods, Map.class);
        System.err.println("商品信息是：" + goods);
        return new HashMap(){{
            put("code", 200);
            put("msg", "success");
        }};
    }

    /**
     * 限流测试接口
     * @return
     */
    @GetMapping("test")
    public String test() {
        return "Order Test!";
    }


    @GetMapping("detail")
    @SentinelResource(fallback = "detailError")//设置该方法对应的降级方法 替身
    public String test2(@RequestParam int id) {
        if (id <= 0){
            System.err.println(1/0);//故意出错
        }
        return "000";
    }
    //定义降级方法 //要求 1返回值类型一样 2参数列表一样
    public String  detailError(@RequestParam int id){
        return "亲，触发熔断降级！";
    }



    // 演示慢调用触发熔断
    @RequestMapping("test5")
    public String test5(String flag)throws  Exception{
        if (flag == null) {
            Thread.sleep(1800);
        }
        return "test5";
    }

    @RequestMapping("test6")
    public String test6(String flag)throws  Exception{
        if (flag == null) {
            throw  new IllegalArgumentException("参数异常");
        }
        return "test6";
    }

    /**
     * 热点参数限流
     * @param name 参数名字
     * @return
     */
    @RequestMapping("test7")
    @SentinelResource("test7-hotkey")
    public String test7(String name) {
        System.out.println(name);
        return name;
    }
}
