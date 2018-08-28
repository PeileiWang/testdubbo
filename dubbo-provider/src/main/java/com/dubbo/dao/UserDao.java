package com.dubbo.dao;

import com.dubbo.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    @Select("select * from user order by id asc")
    List<User> selectAll();

    @Insert("insert into user(id, name, birthday, address) values(#{id}, #{name}, #{birthday},#{address})")
    void insert(User user);

    @Delete("delete from user where id = #{id}")
    void deleteUser(int id);

    @Select("select * from user where id = #{id}")
    User selectById(int id);

    @Update("update user set name = #{name}, birthday = #{birthday}, address = #{address} where id = #{id}")
    void updateUser(User user);
}
