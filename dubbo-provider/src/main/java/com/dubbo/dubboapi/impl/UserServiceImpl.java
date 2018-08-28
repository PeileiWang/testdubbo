package com.dubbo.dubboapi.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.dao.UserDao;
import com.dubbo.dubboapi.UserService;
import com.dubbo.domain.User;
import com.dubbo.rabbitmq.Message;
import com.dubbo.rabbitmq.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service(version = "1-1-0", timeout = 3000, retries = 1, loadbalance = "random")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource(name = "redisTemplate")
    private HashOperations hOps;

    @Autowired
    Sender sender;

    @Override
    public List<User> selectAll() {
        String key = "user_all";
        boolean hasKey = redisTemplate.hasKey(key);

        if (hasKey) {
            List<User> users = (List<User>) redisTemplate.opsForValue().get(key);
            logger.info("selectAll从缓存中获得了所有人员");
            return users;
        }
        List<User> users = userDao.selectAll();
        redisTemplate.opsForValue().set(key, users, 30, TimeUnit.SECONDS);
        logger.info("selectAll从数据库中获得了所有人员");
        return users;
    }

    @Override
    public void insert(User user) {
        String key = "user_" + user.getId();
        boolean haskey = redisTemplate.hasKey(key);
        if (haskey) {
            redisTemplate.delete(key);
        }
        userDao.insert(user);
        boolean hasTotalKey = redisTemplate.hasKey("user_all");
        if (hasTotalKey) {
            redisTemplate.delete("user_all");
        }
        logger.info("insert方法在数据库中添加了 " + key);
    }

    @Override
    public void deleteUser(int id) {
        Message message = new Message();
        message.setId(id);
        message.setMsg("delete");
        sender.sendMessage(message);
    }

    @Override
    public User selectById(int id) {
        String key = "user_" + id;
        User user = null;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            user = (User) redisTemplate.opsForValue().get(key);
            if(user != null)
                logger.info("从缓存中获取了user");
        }
        else {
            user = userDao.selectById(id);
            if(user != null) {
                redisTemplate.opsForValue().set(key, user);
                logger.info("从数据库中获取了user");
            }
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

}
