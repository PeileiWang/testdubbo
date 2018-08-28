package com.dubbo.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public static final String USER_QUEUE = "user.queue";

    @Bean
    public Queue queue() {
        return new Queue(USER_QUEUE);
    }

}
