package com.dubbo.dubboapi;

import com.dubbo.domain.User;

import java.util.List;

public interface UserService {

    List<User> selectAll();

    void insert(User user);

    void deleteUser(int id);

    User selectById(int id);

    void updateUser(User user);

}
