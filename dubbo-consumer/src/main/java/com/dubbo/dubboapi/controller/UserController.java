package com.dubbo.dubboapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.domain.Result;
import com.dubbo.dubboapi.UserService;
import com.dubbo.domain.User;
import com.dubbo.resultenum.ResEnum;
import com.dubbo.utils.ResultUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(version = "1-1-0")
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Result init() {
        return ResultUtil.success(ResEnum.SUCCESS);
    }

    @GetMapping("/searchall")
    public Result<List<User>> searchAll() {
        return ResultUtil.success(ResEnum.SUCCESS, userService.selectAll());
    }

    @PostMapping("/insertuser")
    public Result insert(@RequestBody User user) {
        userService.insert(user);
        return ResultUtil.success(ResEnum.SUCCESS);
    }

    @RequestMapping("/deleteuser")
    public String deleteUser(int id) {
        userService.deleteUser(id);
        return "delete" + id + "succeed!";
    }

    @PostMapping("/updateuser")
    public Result updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResultUtil.success(ResEnum.SUCCESS);
    }

    @RequestMapping("/selectbyid")
    public Result<User> selectById(int id) {
        return ResultUtil.success(ResEnum.SUCCESS, userService.selectById(id));
    }


    @RequestMapping("/testtrans")
    @Transactional
    public Result test(@RequestBody List<User> userList) {
        for (User user :
                userList) {
            userService.insert(user);
        }
        return ResultUtil.success(ResEnum.SUCCESS);
    }
}
