package org.tinkerbell.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;
import org.tinkerbell.entiry.Bar;
import org.tinkerbell.entiry.Foo;

/**
 * Created by nn_liu on 2017/4/6.
 */
@Component
public class ReceiverService {

    /**
     * RabbitListener: handle the message,such as write the data to DB again, send email to others to warn something.
     * @param foo
     */
    @RabbitListener(queues = "queue.foo")
    public void receiveFooQueue(Foo foo, RabbitProperties.Cache.Channel channel) {

        System.out.println("Received Foo<" + foo.getName() + ">");
    }

    @RabbitListener(queues = "queue.bar")
    public void receiveBarQueue(Bar bar) {
        System.out.println("Received Bar<" + bar.getAge() + ">");
    }


}
