package com.spring.boot.base.rabbitMqConfig;

import com.spring.boot.base.BaseController;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/rabbitTest")
public class RabbitController extends BaseController implements RabbitTemplate.ConfirmCallback {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }


    @ResponseBody
    @RequestMapping("/sendMsg")
    public void sendMsg(){
        Object content = "我就发个消息试试";
        //exchange:交换机名称  routingKey:路由关键字  object:发送的消息内容  correlationData:消息ID
        //convertAndSend(String exchange, String routingKey, final Object object, CorrelationData correlationData)
        rabbitTemplate.convertAndSend(ExchangeType.DIRECT.getName(),RoutingKey.ROUTER_A.getName(),content, RabbitUtil.getCorrelationData());
    }

    @ResponseBody
    @RequestMapping("/send")
    public void send(){
        Object content = "open your eyes";
        //exchange:交换机名称  routingKey:路由关键字  object:发送的消息内容  correlationData:消息ID
        //convertAndSend(String exchange, String routingKey, final Object object, CorrelationData correlationData)
        rabbitTemplate.convertAndSend(ExchangeType.DIRECT.getName(),"router_c",content, RabbitUtil.getCorrelationData());
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info("消息回调结果为："+b+",s:"+s);
    }
}
