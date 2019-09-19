package com.spring.boot.base.rabbitMqConfig;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

@Controller
@RabbitListener(queues = "boot-queue")
public class ConsumerController {
    @RabbitHandler
    public void process(String msg){
        System.out.println("topicexchange message 消费者  : " +msg);
    }


}
