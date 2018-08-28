package com.dubbo.dubboapi.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.dubboapi.DemoService;
import com.dubbo.domain.Phone;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String str) {
        return "hello " + str;
    }

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public List<Phone> getAll() {
        List<Phone> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Phone phone = new Phone(i , i + "d");
            list.add(phone);
        }
        return list;
    }

    @Override
    public List<Phone> setAndGet(List<Phone> phones) {
        for (Phone phone:
             phones) {
            phone.setId(phone.getId() + 5);
        }
        return phones;
    }
}
