package org.tinkerbell.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

/**
 * Created by nn_liu on 2017/4/6.
 */
@Configuration
public class ProducerConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queueFoo(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue("queue.foo", true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public Queue queueBar(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue("queue.bar", true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public TopicExchange exchange(RabbitAdmin rabbitAdmin) {
        TopicExchange topicExchange = new TopicExchange("exchange");
        rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }

    /**
     * Binding:Binding联系了Exchange与Message Queue。Exchange在与多个Message Queue发生Binding后会生成一张路由表，
     * 路由表中存储着Message Queue所需消息的限制条件即Binding Key。
     *
     * @param queueFoo
     * @param exchange
     * @param rabbitAdmin
     * @return
     */
    @Bean
    public Binding bindingExchangeFoo(Queue queueFoo, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueFoo).to(exchange).with("queue.foo");
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public Binding bindingExchangeBar(Queue queueBar, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queueBar).to(exchange).with("queue.bar");
        rabbitAdmin.declareBinding(binding);
        return binding;
    }


    /**
     * 生产者用
     *
     * @return
     */
    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setMessageConverter(jackson2Converter());
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        return rabbitMessagingTemplate;
    }

    @Bean
    public MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }
}
