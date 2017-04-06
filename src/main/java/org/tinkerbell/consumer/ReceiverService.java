package org.tinkerbell.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.tinkerbell.entiry.Bar;
import org.tinkerbell.entiry.Foo;

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

    /**
     * RabbitListener: handle the message,such as write the data to DB again, send email to others to warn something.
     *
     * @param foo
     */
    @RabbitListener(queues = "queue.foo")
    public void receiveFooQueue(Foo foo) {
        System.out.println("Received Foo<" + foo.getName() + ">");
    }

    @RabbitListener(queues = "queue.bar")
    public void receiveBarQueue(Bar bar) {
        System.out.println("Received Bar<" + bar.getAge() + ">");
    }


}
