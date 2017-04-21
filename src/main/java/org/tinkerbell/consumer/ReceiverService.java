package org.tinkerbell.consumer;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @RabbitListener用于注册Listener时使用的信息：如queue，exchange，key、ListenerContainerFactory和RabbitAdmin的bean name.
 * @RabbitListener(containerFactory = "rabbitListenerContainerFactory", bindings = @QueueBinding(
 * value = @Queue(value = "${mq.config.queue}", durable = "true"),
 * exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.TOPIC),
 * key = "${mq.config.key}"), admin = "rabbitAdmin")
 * Created by nn_liu on 2017/4/6.
 */
@Component
public class ReceiverService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private CachingConnectionFactory rabbitConnectionFactory;

    /**
     * RabbitListener: handle the message,such as write the data to DB again, send email to others to warn something.
     *
     * @param foo
     */
    @RabbitListener(queues = "queue.foo")
    public String receiveFooQueue(String foo, @Header("foo_queue_header") String sendThread) throws IOException {

        System.out.println("sender thead info:" + sendThread);
        System.out.println("Received Foo<" + foo + ">");
        return "success";
    }

    @RabbitListener(queues = "queue.bar")
    public void receiveBarQueue(String bar) {
        System.out.println("Received Bar<" + bar + ">");
    }


    /*通过Template获取消息*/
    public void receiveBarQueueWithTemplate(){
        String receivedBar = (String) rabbitTemplate.receiveAndConvert("queue.bar");
        System.out.println("receive msg:"+receivedBar);
    }

}
