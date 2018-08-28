package com.dubbo.dubboapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.dubboapi.GoodsService;
import com.dubbo.domain.Goods;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    GoodsService goodsService;



    @RequestMapping("/")
    public String init() {
        return "Init goods";
    }

    @RequestMapping("/set")
    public String set(@RequestBody Goods goods) {
        goodsService.set("goods", goods);
        return "set goods success";
    }

    @RequestMapping("/get")
    public Goods get() {
        return goodsService.get("goods");
    }

    @RequestMapping("/setlist")
    public String setList(String key, @RequestBody List<Object> goodsList) {
        return goodsService.setList(key, goodsList);
    }

    @RequestMapping("/getlist")
    public List<Object> getList(String key) {
        return goodsService.getList(key);
    }





}
