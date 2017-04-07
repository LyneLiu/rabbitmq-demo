package org.tinkerbell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.tinkerbell.consumer.ReceiverService;
import org.tinkerbell.producer.SenderService;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(basePackages = "org.tinkerbell")
public class RabbitmqDemoApplication implements CommandLineRunner {

    @Autowired
    private SenderService senderService;

    @Autowired
    private ReceiverService receiverService;

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Random random = new Random();
        /*while (true) {
            senderService.sendBar2Rabbitmq("bar-" + random.nextInt());
            senderService.sendFoo2Rabbitmq("foo-" + UUID.randomUUID().toString());
            Thread.sleep(1000);
        }*/
    }
}
