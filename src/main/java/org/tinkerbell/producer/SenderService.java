package org.tinkerbell.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.*;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 通过RabbitMessagingTemplate发送message
 * Created by nn_liu on 2017/4/6.
 */
@Component
public class SenderService implements ReturnCallback,ConfirmCallback {

    public static final Logger logger = LoggerFactory.getLogger(SenderService.class);

    @Autowired
    private RabbitTemplate rabbitMessagingTemplate;

    @PostConstruct
    public void init(){
        rabbitMessagingTemplate.setConfirmCallback(this);
    }

    public void sendFoo2Rabbitmq(final String foo) {

        this.rabbitMessagingTemplate.convertAndSend( "exchange","queue.foo", foo);
    }

    public void sendBar2Rabbitmq(final String bar){
        this.rabbitMessagingTemplate.convertAndSend("exchange","queue.bar", bar);
    }

    /**
     *
     * RabbitMQ实现延迟队列（http://blog.csdn.net/u010046908/article/details/57079566）
     * 1、通过队列属性设置，队列中所有的消息都有相同的过期时间；
     * 2、对消息单独设置，每条消息TTL可以不同。
     * @param foo
     */
    public void sendFoo2RabbitmqWithCallBack(final String foo){

        final int xdelay= 300*1000;

        Object response = rabbitMessagingTemplate.convertSendAndReceive("exchange", "queue.foo", foo, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                /*设置当前消息的properties信息*/
                message.getMessageProperties().setHeader("foo_queue_header", Thread.currentThread().getName().toString());

                /*设置消息持久化*/
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);

                /*设置延时发送消息时间（5分钟）*/
                message.getMessageProperties().setDelay(xdelay);

                return message;
            }
        });
        System.out.println(response.toString());
    }

    /**
     * 当为TRUE时，如果消息无法发送到指定的消息队列那么ReturnCallBack回调方法会被调用。
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
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