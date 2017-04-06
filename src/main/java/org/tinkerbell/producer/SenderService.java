package org.tinkerbell.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.*;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tinkerbell.entiry.Bar;
import org.tinkerbell.entiry.Foo;

import javax.annotation.PostConstruct;

/**
 * 通过RabbitMessagingTemplate发送message
 * Created by nn_liu on 2017/4/6.
 */
@Component
public class SenderService implements ReturnCallback,ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitMessagingTemplate;

    @PostConstruct
    public void init(){
        rabbitMessagingTemplate.setConfirmCallback(this);
    }

    public void sendFoo2Rabbitmq(final Foo foo) {
        this.rabbitMessagingTemplate.convertAndSend( "exchange","queue.foo", foo);
    }

    public void sendBar2Rabbitmq(final Bar bar){
        this.rabbitMessagingTemplate.convertAndSend("exchange","queue.bar", bar);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("================");
        System.out.println("message = " + message);
        System.out.println("replyCode = " + replyCode);
        System.out.println("replyText = " + replyText);
        System.out.println("exchange = " + exchange);
        System.out.println("routingKey = " + routingKey);
        System.out.println("================");
    }

    /**
     * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
     * 注意，消息回调只能代表成功消息发送到RabbitMQ服务器，不能代表消息被成功处理和接受
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("================");
        System.out.println("correlationData = " + correlationData);
        System.out.println("ack = " + ack);
        System.out.println("cause = " + cause);
        System.out.println("================");
    }
}