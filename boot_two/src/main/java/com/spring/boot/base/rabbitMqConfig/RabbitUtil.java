package com.spring.boot.base.rabbitMqConfig;

import org.springframework.amqp.rabbit.connection.CorrelationData;

import java.util.UUID;

public class RabbitUtil {

    public static CorrelationData getCorrelationData(){
        return new CorrelationData(UUID.randomUUID().toString().replaceAll("-",""));
    }

}
