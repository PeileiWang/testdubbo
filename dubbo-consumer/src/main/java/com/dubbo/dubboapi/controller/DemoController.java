package com.dubbo.dubboapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.dubboapi.DemoService;
import com.dubbo.domain.Phone;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/test")
public class DemoController {

    @Reference
    DemoService demoService;

    @RequestMapping("/")
    public String init() {
        return "Hello, Spring boots";
    }

    @RequestMapping("/dubbo")
    public String sayHello() {
        return demoService.sayHello("dubbo");
    }

    @RequestMapping("/add")
    public String add(String a, String b) {
        int a1 = Integer.parseInt(a);
        int b1 = Integer.parseInt(b);
        return demoService.add(a1,b1) + "";
    }

    @RequestMapping("/phone")
    public List<Phone> phone() {
        return demoService.getAll();
    }

    @RequestMapping("/setget")
    public List<Phone> setAndGet(@RequestBody List<Phone> phones) {
        return demoService.setAndGet(phones);
    }
}
