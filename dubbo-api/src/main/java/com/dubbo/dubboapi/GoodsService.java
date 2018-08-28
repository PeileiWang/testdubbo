package com.dubbo.dubboapi;

import com.dubbo.domain.Goods;

import java.util.List;

public interface GoodsService {
    Goods get(String key);
    String set(String key, Object obj);
//    String rabbitSet(String key, Object obj);
//    String rabbitGet(String key);
    String setList(String key, List<Object> list);
    List<Object> getList(String key);
}
