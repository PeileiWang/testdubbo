package com.dubbo.dubboapi;



import com.dubbo.domain.Phone;

import java.util.List;

public interface DemoService {
    String sayHello(String str);

    int add(int a, int b);

    List<Phone> getAll();

    List<Phone> setAndGet(List<Phone> phones);
}
