package com.dubbo.rabbitmq;

import com.dubbo.dao.UserDao;
import com.dubbo.domain.User;
import com.dubbo.dubboapi.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = RabbitConfig.USER_QUEUE)
    public void receive(Message message) {
        int id = message.getId();
        String key = "user_" + id;
        String msg = message.getMsg();
        if (msg.equals("delete")) {
            userDao.deleteUser(id);
            boolean hasTotalKey = redisTemplate.hasKey("user_all");
            if (hasTotalKey) {
                redisTemplate.delete("user_all");
            }
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                redisTemplate.delete(key);
                logger.info("delete方法从缓存中删除了 " + key);
            }
            logger.info("delete方法从数据库中删除了 " + key);
        }
    }
}
