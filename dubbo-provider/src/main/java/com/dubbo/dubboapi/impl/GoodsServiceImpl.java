package com.dubbo.dubboapi.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.domain.Goods;
import com.dubbo.dubboapi.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;


import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate")
    private ListOperations<String, List<Object>> listOps;

    @Override
    public Goods get(String key) {
        return (Goods) redisTemplate.opsForValue().get(key);
    }

    @Override
    public String set(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
        return "Success";
    }

    @Override
    public String setList(String key, List<Object> list) {
        listOps.leftPush(key, list);
        return "Success GoodsList leftPush";
    }

    @Override
    public List<Object> getList(String key) {
        return listOps.leftPop(key);
    }
}
