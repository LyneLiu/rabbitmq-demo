package org.tinkerbell.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tinkerbell.entiry.Bar;
import org.tinkerbell.entiry.Foo;

/**
 * 通过RabbitMessagingTemplate发送message
 * Created by nn_liu on 2017/4/6.
 */
@Component
public class SenderService implements ReturnCallback {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    public void sendFoo2Rabbitmq(final Foo foo) {
        this.rabbitMessagingTemplate.convertAndSend( "exchange","queue.foo", foo);
    }

    public void sendBar2Rabbitmq(final Bar bar){
        this.rabbitMessagingTemplate.convertAndSend("exchange","queue.bar", bar);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println(message.getBody());
    }
}